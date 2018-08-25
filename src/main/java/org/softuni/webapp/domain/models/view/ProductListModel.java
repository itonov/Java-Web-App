package org.softuni.webapp.domain.models.view;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

public class ProductListModel {
    private String id;

    private String make;

    private String model;

    private BigDecimal price;

    private String firstPictureBase64String;

    public ProductListModel() {

    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getFirstPictureBase64String() {
        return this.firstPictureBase64String;
    }

    public void setFirstPictureBase64String(String firstPictureBase64String) {
        this.firstPictureBase64String = firstPictureBase64String;
    }
}
