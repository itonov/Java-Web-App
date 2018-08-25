package org.softuni.webapp.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.domain.entities.enums.ProductCategory;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product implements Serializable {
    private String id;

    private String make;

    private String model;

    private String description;

    private BigDecimal price;

    private List<Picture> pictures;

    private ProductCategory productCategory;

    private Boolean isUsed;

    public Product() {
        this.pictures = new ArrayList<>();
    }

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull(message = AppConstants.MAKE_CANNOT_BE_NULL)
    @Size(min = 2, max = 10, message = AppConstants.MAKE_MODEL_ALLOWED_SIZE)
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotNull(message = AppConstants.MODEL_CANNOT_BE_NULL)
    @Size(min = 2, max = 10, message = AppConstants.MAKE_MODEL_ALLOWED_SIZE)
    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Lob
    @Column(length = 512)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotNull(message = AppConstants.PRICE_CANNOT_BE_NULL)
    @Digits(integer = 6, fraction = 2)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Size(max = 6, message = AppConstants.PICTURES_MAX_COUNT)
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Picture.class)
    public List<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    @NotNull(message = AppConstants.PRODUCT_CATEGORY_CANNOT_BE_NULL)
    @Enumerated(EnumType.STRING)
    public ProductCategory getProductCategory() {
        return this.productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    @NotNull(message = AppConstants.PRODUCT_IS_USED_CANNOT_BE_NULL)
    public Boolean getIsUsed() {
        return this.isUsed;
    }

    public void setIsUsed(Boolean isUsed) {
        this.isUsed = isUsed;
    }
}
