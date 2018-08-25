package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.Product;
import org.softuni.webapp.domain.entities.enums.ProductCategory;
import org.softuni.webapp.domain.models.binding.ProductAddBindingModel;
import org.softuni.webapp.domain.models.binding.ProductEditBindingModel;
import org.softuni.webapp.domain.models.view.ProductDetailsModel;
import org.softuni.webapp.domain.models.view.ProductListModel;

import java.util.List;

public interface ProductService {
    void saveAddProductModel(ProductAddBindingModel product);

    List<Product> receiveAll();

    List<ProductListModel> receiveAllAsListModels();

    ProductEditBindingModel findEditModelById(String productId);

    ProductDetailsModel findDetailsModelById(String productId);

    List<ProductListModel> receiveAllByCategory(ProductCategory productCategory);

    void deleteProductById(String productId);

    void saveEditProductModel(ProductEditBindingModel productEditBindingModel, String productId);

    Product findById(String productId);
}
