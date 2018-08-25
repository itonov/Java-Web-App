package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.UserRole;

public interface UserRoleService {
    UserRole getUserRoleByAuthority(String authorityName);
}
