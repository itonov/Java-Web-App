package org.softuni.webapp.services;

import org.modelmapper.ModelMapper;
import org.softuni.webapp.domain.entities.Picture;
import org.softuni.webapp.domain.entities.Product;
import org.softuni.webapp.domain.entities.enums.ProductCategory;
import org.softuni.webapp.domain.models.binding.ProductAddBindingModel;
import org.softuni.webapp.domain.models.binding.ProductEditBindingModel;
import org.softuni.webapp.domain.models.view.ProductDetailsModel;
import org.softuni.webapp.domain.models.view.ProductListModel;
import org.softuni.webapp.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;

    private ModelMapper modelMapper;

    private PictureService pictureService;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              PictureService pictureService,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.pictureService = pictureService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void saveAddProductModel(ProductAddBindingModel productModel) {
        List<Picture> pictures = this.pictureService.processAndUploadMultipartPictures(productModel.getPictures());
        pictures.forEach(p -> this.pictureService.savePictureToDb(p));

        Product newProduct = this.modelMapper.map(productModel, Product.class);
        newProduct.setPictures(pictures);

        this.productRepository.save(newProduct);
    }

    @Override
    public List<Product> receiveAll() {
        return this.productRepository.findAll();
    }

    @Override
    public List<ProductListModel> receiveAllAsListModels() {
        return this.receiveAll().stream().map(p -> {
            ProductListModel newModel = new ProductListModel();

            this.modelMapper.map(p, newModel);

            if (!p.getPictures().isEmpty()) {
                String mainPictureCloudId = p.getPictures().get(0).getCloudId();

                String base64StringContent = this.pictureService.receivePictureContentAsBase64(mainPictureCloudId);

                newModel.setFirstPictureBase64String(base64StringContent);
            }

            return newModel;
        }).collect(Collectors.toList());
    }

    @Override
    public ProductEditBindingModel findEditModelById(String productId) {
        Product productFromDb = this.productRepository.findProductById(productId);

        ProductEditBindingModel model = this.modelMapper.map(productFromDb, ProductEditBindingModel.class);

        if (!productFromDb.getPictures().isEmpty()) {
            List<String> picturesBase64String = productFromDb.getPictures()
                    .stream()
                    .map(p -> this.pictureService.receivePictureContentAsBase64(p.getCloudId()))
                    .collect(Collectors.toList());

            model.setPicturesContentsAsBase64(picturesBase64String);
        }

        return model;
    }

    @Override
    public ProductDetailsModel findDetailsModelById(String productId) {
        Product productFromDb = this.productRepository.findProductById(productId);

        ProductDetailsModel model = new ProductDetailsModel();
        this.modelMapper.map(productFromDb, model);

        if (!productFromDb.getPictures().isEmpty()) {
            List<String> picturesBase64String = productFromDb.getPictures()
                    .stream()
                    .map(p -> this.pictureService.receivePictureContentAsBase64(p.getCloudId()))
                    .collect(Collectors.toList());

            model.setPicturesContentsAsBase64(picturesBase64String);
        }

        return model;
    }

    @Override
    public List<ProductListModel> receiveAllByCategory(ProductCategory category) {
        List<Product> productsFromDb = this.productRepository.findAllProductsByProductCategory(category);

        List<ProductListModel> result = productsFromDb.stream().map(p -> {
            ProductListModel newModel = new ProductListModel();

            this.modelMapper.map(p, newModel);

            if (!p.getPictures().isEmpty() && p.getPictures().size() > 0) {
                String mainPictureCloudId = p.getPictures().get(0).getCloudId();
                String mainPictureBase64String = this.pictureService.receivePictureContentAsBase64(mainPictureCloudId);

                newModel.setFirstPictureBase64String(mainPictureBase64String);
            }

            return newModel;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void deleteProductById(String productId) {
        List<Picture> picturesToDelete = this.productRepository.findProductById(productId).getPictures();

        if (!picturesToDelete.isEmpty()) {
            picturesToDelete.forEach(p -> this.pictureService.deletePictureFromCloud(p.getCloudId()));
        }
        this.productRepository.deleteById(productId);
    }

    @Override
    public void saveEditProductModel(ProductEditBindingModel productEditBindingModel,
                                     String productId) {
        Product productFromDb = this.productRepository.findProductById(productId);

        this.modelMapper.map(productEditBindingModel, productFromDb);

        List<Picture> oldPicturesToRemove = new ArrayList<>();

        if (!productEditBindingModel.getNewPictures().isEmpty()) {
            oldPicturesToRemove = productFromDb.getPictures();

            oldPicturesToRemove.forEach(p -> this.pictureService.deletePictureFromCloud(p.getCloudId()));

            List<Picture> newPictures = this.pictureService.processAndUploadMultipartPictures(productEditBindingModel.getNewPictures());
            newPictures.forEach(p -> this.pictureService.savePictureToDb(p));

            productFromDb.setPictures(newPictures);
        }

        this.productRepository.save(productFromDb);

        if (!oldPicturesToRemove.isEmpty()) {
            oldPicturesToRemove.forEach(p -> this.pictureService.deletePictureFromDb(p.getId()));
        }
    }

    @Override
    public Product findById(String productId) {
        return this.productRepository.findById(productId).orElse(null);
    }
}
