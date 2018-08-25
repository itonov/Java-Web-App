package org.softuni.webapp.services;

import org.modelmapper.ModelMapper;
import org.softuni.webapp.domain.entities.Part;
import org.softuni.webapp.domain.entities.Picture;
import org.softuni.webapp.domain.models.binding.PartAddBindingModel;
import org.softuni.webapp.domain.models.binding.PartEditBindingModel;
import org.softuni.webapp.domain.models.view.PartDetailsModel;
import org.softuni.webapp.domain.models.view.PartListModel;
import org.softuni.webapp.repositories.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService {
    private PartRepository partRepository;

    private ModelMapper modelMapper;

    private PictureService pictureService;

    @Autowired
    public PartServiceImpl(PartRepository partRepository, PictureService pictureService, ModelMapper modelMapper) {
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
    }

    @Override
    public List<Part> findAll() {
        return this.partRepository.findAll();
    }

    @Override
    public List<PartListModel> findAllAsListModels() {
        List<Part> partsFromDb = this.findAll();

        List<PartListModel> result = partsFromDb.stream().map(p -> {
            PartListModel resultPart = new PartListModel();

            this.modelMapper.map(p, resultPart);

            if (!p.getPictures().isEmpty()) {
                String mainPictureCloudId = p.getPictures().get(0).getCloudId();

                String base64StringContent = this.pictureService.receivePictureContentAsBase64(mainPictureCloudId);

                resultPart.setFirstPictureBase64String(base64StringContent);
            }

            return resultPart;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void saveAddModelPart(PartAddBindingModel partModel) {
        List<Picture> pictures = this.pictureService.processAndUploadMultipartPictures(partModel.getPictures());
        pictures.forEach(p -> this.pictureService.savePictureToDb(p));

        Part newPart = this.modelMapper.map(partModel, Part.class);
        newPart.setPictures(pictures);

        this.partRepository.save(newPart);
    }

    @Override
    public PartDetailsModel findDetailsModelById(String partId) {
        Part partFromDb = this.partRepository.findPartById(partId);

        PartDetailsModel partDetailsModel = new PartDetailsModel();
        this.modelMapper.map(partFromDb, partDetailsModel);

        if (!partFromDb.getPictures().isEmpty()) {
            List<String> picturesBase64String = partFromDb.getPictures()
                    .stream()
                    .map(p -> this.pictureService.receivePictureContentAsBase64(p.getCloudId()))
                    .collect(Collectors.toList());

            partDetailsModel.setPicturesContentsAsBase64(picturesBase64String);
        }

        return partDetailsModel;
    }

    @Override
    public void deletePartById(String partId) {
        List<Picture> picturesToDelete = this.partRepository.findPartById(partId).getPictures();

        if (!picturesToDelete.isEmpty()) {
            picturesToDelete.forEach(p -> this.pictureService.deletePictureFromCloud(p.getCloudId()));
        }

        this.partRepository.deleteById(partId);
    }

    @Override
    public PartEditBindingModel findEditModelById(String partId) {
        Part partFromDb = this.partRepository.findPartById(partId);

        PartEditBindingModel model = this.modelMapper.map(partFromDb, PartEditBindingModel.class);

        if (!partFromDb.getPictures().isEmpty()) {
            List<String> picturesAsBase64String = partFromDb.getPictures()
                    .stream()
                    .map(p -> this.pictureService.receivePictureContentAsBase64(p.getCloudId()))
                    .collect(Collectors.toList());

            model.setPicturesContentsAsBase64(picturesAsBase64String);
        }

        return model;
    }

    @Override
    public void saveEditModel(PartEditBindingModel partEditBindingModel, String partId) {
        Part partFromDb = this.partRepository.findPartById(partId);

        this.modelMapper.map(partEditBindingModel, partFromDb);

        List<Picture> oldPicturesToRemove = new ArrayList<>();

        if (!partEditBindingModel.getNewPictures().isEmpty()) {
            oldPicturesToRemove = partFromDb.getPictures();

            oldPicturesToRemove.forEach(p -> this.pictureService.deletePictureFromCloud(p.getCloudId()));

            List<Picture> newPictures = this.pictureService.processAndUploadMultipartPictures(partEditBindingModel.getNewPictures());
            newPictures.forEach(p -> this.pictureService.savePictureToDb(p));

            partFromDb.setPictures(newPictures);
        }

        this.partRepository.save(partFromDb);

        if (!oldPicturesToRemove.isEmpty()) {
            oldPicturesToRemove.forEach(p -> this.pictureService.deletePictureFromDb(p.getId()));
        }
    }

    @Override
    public Part findById(String partId) {
        return this.partRepository.findById(partId).orElse(null);
    }
}
