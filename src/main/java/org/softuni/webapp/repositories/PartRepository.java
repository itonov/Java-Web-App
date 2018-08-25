package org.softuni.webapp.repositories;

import org.softuni.webapp.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, String> {

    Part findPartById(String partId);
}
