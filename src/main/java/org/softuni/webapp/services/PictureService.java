package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.Picture;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PictureService {
    List<Picture> processAndUploadMultipartPictures(List<MultipartFile> pictures);

    String receivePictureContentAsBase64(String pictureCloudId);

    void savePictureToDb(Picture picture);

    void deletePictureFromCloud(String cloudId);

    void deletePictureFromDb(String pictureId);
}
