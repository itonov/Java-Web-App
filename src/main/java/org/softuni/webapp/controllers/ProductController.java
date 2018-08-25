package org.softuni.webapp.controllers;

import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.constants.TitleConstants;
import org.softuni.webapp.domain.entities.enums.ProductCategory;
import org.softuni.webapp.domain.models.view.ProductDetailsModel;
import org.softuni.webapp.domain.models.view.ProductListModel;
import org.softuni.webapp.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/all")
    public String allProducts(Model model) {
        List<ProductListModel> allProducts = this.productService.receiveAllAsListModels();

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.ALL_PRODUCTS_VALUE);
        model.addAttribute(AppConstants.ALL_PRODUCTS_NAME, allProducts);
        model.addAttribute(AppConstants.CATEGORY_TYPE_NAME, AppConstants.CATEGORY_TYPE_ALL_NAME);

        return "products/all";
    }

    @GetMapping("/products/details/{id}")
    public String productDetails(@PathVariable(name = "id") String id, Model model) {
        ProductDetailsModel productDetailsModel = this.productService.findDetailsModelById(id);

        model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.DETAILS_FOR_VALUE
                + productDetailsModel.getMake()
                + " "
                + productDetailsModel.getModel());
        model.addAttribute(AppConstants.PRODUCT_DETAILS_MODEL_NAME, productDetailsModel);
        model.addAttribute(AppConstants.PRODUCT_ID_NAME, id);

        return "products/details";
    }

    @GetMapping("/products/category/{category}")
    public String allProductsByCategory(@PathVariable(name = "category") String category,
                                        Model model) {
        ProductCategory neededCategory = null;
        switch (category) {
            case "whiteTech":
                neededCategory = ProductCategory.WHITE_TECH;
                model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.WHITE_TECH_VALUE);
                break;
            case "blackTech":
                neededCategory = ProductCategory.BLACK_TECH;
                model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.BLACK_TECH_VALUE);
                break;
            case "other":
                neededCategory = ProductCategory.OTHER;
                model.addAttribute(TitleConstants.TITLE_NAME, TitleConstants.OTHERS_VALUE);
                break;
        }

        List<ProductListModel> allProducts = this.productService.receiveAllByCategory(neededCategory);

        model.addAttribute(AppConstants.ALL_PRODUCTS_NAME, allProducts);
        model.addAttribute(AppConstants.CATEGORY_TYPE_NAME, category);

        return "products/all";
    }
}
