package org.softuni.webapp.repositories;

import org.softuni.webapp.domain.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PictureRepository extends JpaRepository<Picture, String> {
    void deleteById(String pictureId);
}
