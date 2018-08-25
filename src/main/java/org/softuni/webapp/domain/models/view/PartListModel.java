package org.softuni.webapp.domain.models.view;

import java.math.BigDecimal;

public class PartListModel {
    private String id;

    private String name;

    private BigDecimal price;

    private Integer quantity;
    
    private String firstPictureBase64String;

    public PartListModel() {
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getFirstPictureBase64String() {
        return this.firstPictureBase64String;
    }

    public void setFirstPictureBase64String(String base64String) {
        this.firstPictureBase64String = base64String;
    }
}
