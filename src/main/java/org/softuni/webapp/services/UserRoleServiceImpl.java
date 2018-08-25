package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.UserRole;
import org.softuni.webapp.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private UserRoleRepository userRoleRepository;

    @Autowired
    public UserRoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getUserRoleByAuthority(String authorityName) {
        return this.userRoleRepository.findByAuthority(authorityName);
    }
}
