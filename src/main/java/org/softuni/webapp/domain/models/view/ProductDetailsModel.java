package org.softuni.webapp.domain.models.view;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductDetailsModel {
    private String make;

    private String model;

    private String description;

    private String category;

    private Boolean isUsed;

    private BigDecimal price;

    private List<String> picturesContentsAsBase64;

    public ProductDetailsModel() {
        this.picturesContentsAsBase64 = new ArrayList<>();
    }

    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getIsUsed() {
        return this.isUsed;
    }

    public void setIsUsed(Boolean used) {
        isUsed = used;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getPicturesContentsAsBase64() {
        return this.picturesContentsAsBase64;
    }

    public void setPicturesContentsAsBase64(List<String> picturesContentsAsBase64) {
        this.picturesContentsAsBase64 = picturesContentsAsBase64;
    }
}
