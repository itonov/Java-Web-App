package org.softuni.webapp.repositories;

import org.softuni.webapp.domain.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, String> {
    UserRole findByAuthority(String authorityName);
}
