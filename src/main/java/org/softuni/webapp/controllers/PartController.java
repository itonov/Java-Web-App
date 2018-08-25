package org.softuni.webapp.controllers;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.TitleConstants;
import org.softuni.webapp.domain.models.view.PartDetailsModel;
import org.softuni.webapp.domain.models.view.PartListModel;
import org.softuni.webapp.services.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PartController {
    private PartService partService;

    @Autowired
    public PartController(PartService partService) {
        this.partService = partService;
    }

    @GetMapping("/parts/all")
    public String allParts(Model model) {
        List<PartListModel> allParts = this.partService.findAllAsListModels();

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ALL_PARTS_VALUE);
        model.addAttribute(AppConstants.ALL_PARTS_NAME, allParts);

        return "parts/all";
    }

    @GetMapping("/parts/details/{id}")
    public String partDetails(@PathVariable(name = "id") String id, Model model) {
        PartDetailsModel partDetailsModel = this.partService.findDetailsModelById(id);

        model.addAttribute(TitleConstants.TITLE_NAME,
                TitleConstants.DETAILS_FOR_VALUE
                        + partDetailsModel.getName());
        model.addAttribute(AppConstants.VIEW_PARTS_NAME, partDetailsModel);
        model.addAttribute(AppConstants.PART_ID_NAME, id);

        return "parts/details";
    }

}
