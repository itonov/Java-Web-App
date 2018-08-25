package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.User;
import org.softuni.webapp.domain.models.binding.UserRegisterBindingModel;
import org.softuni.webapp.domain.models.view.UserListModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Set;

public interface UserService extends UserDetailsService {
    boolean createUser(UserRegisterBindingModel user);

    Set<User> getAllUsers();

    boolean isEmailAlreadyUsed(String emailToCheck);

    boolean isUsernameAlreadyUsed(String usernameToCheck);

    User findById(String userId);

    void saveUser(User user);

    Set<UserListModel> getAllUsersAsListModels();

    UserListModel findUserListModelByUsername(String username);

    void makeUserAdminByUsername(String username);

    void removeAdminRoleFromUser(String username);
}
