package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.Part;
import org.softuni.webapp.domain.models.binding.PartAddBindingModel;
import org.softuni.webapp.domain.models.binding.PartEditBindingModel;
import org.softuni.webapp.domain.models.view.PartDetailsModel;
import org.softuni.webapp.domain.models.view.PartListModel;

import java.util.List;

public interface PartService {
    List<Part> findAll();

    List<PartListModel> findAllAsListModels();

    void saveAddModelPart(PartAddBindingModel part);

    PartDetailsModel findDetailsModelById(String partId);

    void deletePartById(String partId);

    PartEditBindingModel findEditModelById(String partId);

    void saveEditModel(PartEditBindingModel model, String partId);

    Part findById(String partId);
}
