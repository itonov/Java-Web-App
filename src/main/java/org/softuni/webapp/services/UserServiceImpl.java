package org.softuni.webapp.services;

import org.modelmapper.ModelMapper;
import org.softuni.webapp.common.factories.UserRoleFactory;
import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.domain.entities.User;
import org.softuni.webapp.domain.entities.UserRole;
import org.softuni.webapp.domain.models.binding.UserRegisterBindingModel;
import org.softuni.webapp.domain.models.view.UserListModel;
import org.softuni.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserRoleFactory userRoleFactory;

    private UserRoleService userRoleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper modelMapper,
                           BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserRoleFactory userRoleFactory,
                           UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRoleFactory = userRoleFactory;
        this.userRoleService = userRoleService;
    }

    private boolean checkIfUserIsModerator(User user) {
        Set<UserRole> currentRoles = user.getAuthorities();

        for (UserRole currentRole : currentRoles) {
            if (currentRole.getAuthority().equals(AppConstants.ROLE_MODERATOR_NAME)) {
                return true;
            }
        }

        return false;
    }

    private UserListModel mapUserToListModel(User user) {
        UserListModel newModel = this.modelMapper.map(user, UserListModel.class);

        newModel.setRegisteredOn(
                Date.from(user.getRegisteredOn()
                        .atStartOfDay(ZoneId.systemDefault())
                        .toInstant())
        );

        Set<String> authoritiesAsString = user.getAuthorities()
                .stream()
                .map(UserRole::getAuthority)
                .collect(Collectors.toSet());

        newModel.setAuthoritiesAsString(authoritiesAsString);

        return newModel;
    }

    @Override
    public boolean isEmailAlreadyUsed(String emailToCheck) {
        return this.userRepository.findByEmail(emailToCheck) != null;
    }

    @Override
    public boolean isUsernameAlreadyUsed(String usernameToCheck) {
        return this.userRepository.findByUsername(usernameToCheck).orElse(null) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User foundUser = this.userRepository
                .findByUsername(username)
                .orElse(null);

        if (foundUser == null) {
            throw new UsernameNotFoundException(AppConstants.USERNAME_NOT_FOUND);
        }

        return foundUser;
    }

    @Override
    public boolean createUser(UserRegisterBindingModel userRegisterBindingModel) {
        User userEntity = this.modelMapper.map(userRegisterBindingModel, User.class);

        userEntity.setPassword(this.bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRegisteredOn(LocalDate.now());

        Set<UserRole> authorities = new HashSet<>();

        if (this.userRepository.findAll().isEmpty()) {
            authorities.add(this.userRoleFactory.createUserRole(AppConstants.ROLE_USER_NAME));
            authorities.add(this.userRoleFactory.createUserRole(AppConstants.ROLE_ADMIN_NAME));
            authorities.add(this.userRoleFactory.createUserRole(AppConstants.ROLE_MODERATOR_NAME));
        } else {
            authorities.add(this.userRoleService.getUserRoleByAuthority(AppConstants.ROLE_USER_NAME));
        }

        userEntity.setAuthorities(authorities);

        return this.userRepository.save(userEntity) != null;
    }

    @Override
    public Set<User> getAllUsers() {
        return this.userRepository.findAll().stream().collect(Collectors.toUnmodifiableSet());
    }

    @Override
    public User findById(String userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        this.userRepository.save(user);
    }

    @Override
    public Set<UserListModel> getAllUsersAsListModels() {
        Set<User> usersFromDb = this.getAllUsers();

        Set<UserListModel> models = new HashSet<>();

        for (User user : usersFromDb) {
            if (!this.checkIfUserIsModerator(user)) {
                UserListModel newModel = this.mapUserToListModel(user);

                models.add(newModel);
            }
        }

        return models;
    }

    @Override
    public UserListModel findUserListModelByUsername(String username) {
        User userFromDb = this.userRepository.findByUsername(username).orElse(null);

        if (userFromDb != null) {
            return this.mapUserToListModel(userFromDb);
        }

        return null;
    }

    @Override
    public void makeUserAdminByUsername(String username) {
        User userFromDb = this.userRepository.findByUsername(username).orElse(null);

        if (userFromDb != null) {
            UserRole adminRole = this.userRoleService.getUserRoleByAuthority(AppConstants.ROLE_ADMIN_NAME);
            UserRole userRole = this.userRoleService.getUserRoleByAuthority(AppConstants.ROLE_USER_NAME);

            Set<UserRole> newRoles = new HashSet<>();
            newRoles.add(adminRole);
            newRoles.add(userRole);

            userFromDb.setAuthorities(newRoles);

            this.userRepository.save(userFromDb);
        }
    }

    @Override
    public void removeAdminRoleFromUser(String username) {
        User userFromDb = this.userRepository.findByUsername(username).orElse(null);

        if (userFromDb != null) {
            UserRole normalUserRole = this.userRoleService.getUserRoleByAuthority(AppConstants.ROLE_USER_NAME);

            Set<UserRole> newRoles = new HashSet<>();
            newRoles.add(normalUserRole);

            userFromDb.setAuthorities(newRoles);

            this.userRepository.save(userFromDb);
        }
    }
}
