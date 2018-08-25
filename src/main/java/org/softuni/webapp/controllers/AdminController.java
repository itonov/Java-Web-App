package org.softuni.webapp.controllers;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.TitleConstants;
import org.softuni.webapp.domain.models.binding.PartAddBindingModel;
import org.softuni.webapp.domain.models.binding.PartEditBindingModel;
import org.softuni.webapp.domain.models.binding.ProductAddBindingModel;
import org.softuni.webapp.domain.models.binding.ProductEditBindingModel;
import org.softuni.webapp.domain.models.view.OrderListModel;
import org.softuni.webapp.services.OrderService;
import org.softuni.webapp.services.PartService;
import org.softuni.webapp.services.ProductService;
import org.softuni.webapp.util.UploadImageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private ProductService productService;

    private PartService partService;

    private OrderService orderService;

    @Autowired
    public AdminController(ProductService productService,
                           PartService partService,
                           OrderService orderService) {
        this.productService = productService;
        this.partService = partService;
        this.orderService = orderService;
    }

    @GetMapping("/parts/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addPart(@ModelAttribute(name = "partAddBindingModel") PartAddBindingModel partAddBindingModel,
                          Model model) {
        model.addAttribute("partAddBindingModel", partAddBindingModel);
        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ADD_NEW_PART_VALUE);

        return "parts/add";
    }

    @PostMapping("/parts/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addPartConfirm(@Valid PartAddBindingModel partAddBindingModel,
                                 BindingResult bindingResult) {
        partAddBindingModel.setPictures(
                partAddBindingModel.getPictures()
                        .stream()
                        .filter(p -> !p.isEmpty())
                        .collect(Collectors.toList()));

        if (!partAddBindingModel.getPictures().isEmpty()) {
            UploadImageValidator.validateUploadedImages(partAddBindingModel.getPictures(), bindingResult);
        }

        if (bindingResult.hasErrors()) {
            return "parts/add";
        }

        this.partService.saveAddModelPart(partAddBindingModel);

        return "redirect:/parts/all";
    }

    @GetMapping("/parts/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deletePart(@PathVariable(name = "id") String partId) {
        this.partService.deletePartById(partId);

        return "redirect:/parts/all";
    }

    @GetMapping("/parts/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editPart(@PathVariable("id") String partId,
                           @ModelAttribute(name = "partEditBindingModel") PartEditBindingModel partEditBindingModel,
                           Model model) {
        partEditBindingModel = this.partService.findEditModelById(partId);

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.EDIT_PART_VALUE);
        model.addAttribute(AppConstants.PART_ID_NAME, partId);
        model.addAttribute(AppConstants.PART_EDIT_MODEL_NAME, partEditBindingModel);
        model.addAttribute(AppConstants.PART_NAME_VALUE, partEditBindingModel.getName());

        return "parts/edit";
    }

    @PostMapping("/parts/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editPartConfirm(@PathVariable("id") String partId,
                                  @Valid PartEditBindingModel partEditBindingModel,
                                  BindingResult bindingResult,
                                  Model model) {
        partEditBindingModel.setNewPictures(
                partEditBindingModel.getNewPictures()
                .stream()
                .filter(p -> !p.isEmpty())
                .collect(Collectors.toList())
        );

        UploadImageValidator.validateUploadedImages(partEditBindingModel.getNewPictures(), bindingResult);

        if (bindingResult.hasErrors()) {
            PartEditBindingModel freshModel = this.partService.findEditModelById(partId);

            model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.EDIT_PART_VALUE);
            model.addAttribute(AppConstants.PART_ID_NAME, partId);
            model.addAttribute(AppConstants.PART_EDIT_MODEL_NAME, partEditBindingModel);
            model.addAttribute(AppConstants.PART_NAME_VALUE, freshModel.getName());

            return "parts/edit";
        }

        this.partService.saveEditModel(partEditBindingModel, partId);

        return "redirect:/parts/details/" + partId;
    }

    @GetMapping("/products/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteProduct(@PathVariable(name = "id") String productId) {

        this.productService.deleteProductById(productId);

        return "redirect:/products/all";
    }

    @GetMapping("/products/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProduct(@ModelAttribute(name = "productAddBindingModel") ProductAddBindingModel productAddBindingModel,
                             Model model) {
        model.addAttribute(AppConstants.PRODUCT_ADD_MODEL_NAME, productAddBindingModel);
        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ADD_PRODUCT_VALUE);

        return "products/add";
    }

    @PostMapping("/products/add")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String addProductConfirm(@Valid ProductAddBindingModel productAddModel,
                                    BindingResult bindingResult,
                                    Model model) {
        productAddModel.setPictures(
                productAddModel.getPictures()
                        .stream()
                        .filter(p -> !p.isEmpty())
                        .collect(Collectors.toList()));

        UploadImageValidator.validateUploadedImages(productAddModel.getPictures(), bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ADD_PRODUCT_VALUE);

            return "products/add";
        }

        this.productService.saveAddProductModel(productAddModel);

        return "redirect:/products/all";
    }

    @GetMapping("/products/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editProduct(@PathVariable("id") String productId,
                              @ModelAttribute(name = "productEditBindingModel") ProductEditBindingModel productEditBindingModel,
                              Model model) {
        productEditBindingModel = this.productService.findEditModelById(productId);

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.EDIT_PRODUCT_VALUE);
        model.addAttribute(AppConstants.PRODUCT_ID_NAME, productId);
        model.addAttribute(AppConstants.PRODUCT_EDIT_MODEL_NAME, productEditBindingModel);
        model.addAttribute(AppConstants.PRODUCT_MAKE_NAME, productEditBindingModel.getMake());
        model.addAttribute(AppConstants.PRODUCT_MODEL_NAME, productEditBindingModel.getModel());

        return "products/edit";
    }

    @PostMapping("/products/edit/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String editProductConfirm(@PathVariable("id") String productId,
                                     @Valid ProductEditBindingModel productEditBindingModel,
                                     BindingResult bindingResult,
                                     Model model) {
        productEditBindingModel.setNewPictures(
                productEditBindingModel.getNewPictures()
                        .stream()
                        .filter(p -> !p.isEmpty())
                        .collect(Collectors.toList())
        );

        UploadImageValidator.validateUploadedImages(productEditBindingModel.getNewPictures(), bindingResult);

        if (bindingResult.hasErrors()) {
            ProductEditBindingModel freshModel = this.productService.findEditModelById(productId);

            productEditBindingModel.setPicturesContentsAsBase64(freshModel.getPicturesContentsAsBase64());

            model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.EDIT_PRODUCT_VALUE);
            model.addAttribute(AppConstants.PRODUCT_ID_NAME, productId);
            model.addAttribute(AppConstants.PRODUCT_EDIT_MODEL_NAME, productEditBindingModel);
            model.addAttribute(AppConstants.PRODUCT_MAKE_NAME, freshModel.getMake());
            model.addAttribute(AppConstants.PRODUCT_MODEL_NAME, freshModel.getModel());
            return "products/edit";
        }

        this.productService.saveEditProductModel(productEditBindingModel, productId);

        return "redirect:/products/details/" + productId;
    }

    @GetMapping("/orders/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String allOrders(Model model) {
        List<OrderListModel> allOrders = this.orderService.findAllAsListModels();

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ALL_ORDERS_VALUE);
        model.addAttribute(AppConstants.ALL_ORDERS_NAME, allOrders);

        return "orders/all";
    }


}
