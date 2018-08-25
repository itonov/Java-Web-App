package org.softuni.webapp.domain.models.view;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class PartDetailsModel {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private List<String> picturesContentsAsBase64;

    public PartDetailsModel() {
        this.picturesContentsAsBase64 = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public List<String> getPicturesContentsAsBase64() {
        return this.picturesContentsAsBase64;
    }

    public void setPicturesContentsAsBase64(List<String> picturesContentsAsBase64) {
        this.picturesContentsAsBase64 = picturesContentsAsBase64;
    }
}
