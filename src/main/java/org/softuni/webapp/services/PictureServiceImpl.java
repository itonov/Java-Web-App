package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.Picture;
import org.softuni.webapp.repositories.PictureRepository;
import org.softuni.webapp.util.CloudManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PictureServiceImpl implements PictureService {
    private PictureRepository pictureRepository;

    private CloudManager cloudManager;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository,
                              CloudManager cloudManager) {
        this.pictureRepository = pictureRepository;
        this.cloudManager = cloudManager;
    }

    private String uploadFileAndReceiveCloudId(MultipartFile fileToUpload) {
        return this.cloudManager.uploadFileAndReceiveCloudId(fileToUpload);
    }

    @Override
    public List<Picture> processAndUploadMultipartPictures(List<MultipartFile> pictures) {
        List<Picture> resultPictures = pictures.stream().map(file -> {
            String cloudId = this.uploadFileAndReceiveCloudId(file);

            Picture newPicture = new Picture();
            newPicture.setCloudId(cloudId);

            return newPicture;
        }).collect(Collectors.toList());

        return resultPictures;
    }

    @Override
    public String receivePictureContentAsBase64(String pictureCloudId) {
        return this.cloudManager.receivePictureContentAsBase64(pictureCloudId);
    }

    @Override
    public void savePictureToDb(Picture picture) {
        this.pictureRepository.save(picture);
    }

    @Override
    public void deletePictureFromCloud(String cloudId) {
        this.cloudManager.deletePictureByCloudId(cloudId);
    }

    @Override
    public void deletePictureFromDb(String pictureId) {
        this.pictureRepository.deleteById(pictureId);
    }
}
