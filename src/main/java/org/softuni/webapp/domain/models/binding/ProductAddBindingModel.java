package org.softuni.webapp.domain.models.binding;

import org.softuni.webapp.constants.ErrorMessagesBg;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductAddBindingModel {
    private String make;

    private String model;

    private String description;

    private BigDecimal price;

    private String category;

    private Boolean isUsed;

    private List<MultipartFile> pictures;

    public ProductAddBindingModel() {
        this.pictures = new ArrayList<>();
    }

    @NotEmpty(message = ErrorMessagesBg.MAKE_CANNOT_BE_NULL)
    @Size(min = 2, max = 10, message = ErrorMessagesBg.INVALID_LENGTH)
    public String getMake() {
        return this.make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @NotEmpty(message = ErrorMessagesBg.MODEL_CANNOT_BE_NULL)
    @Size(min = 2, max = 10, message = ErrorMessagesBg.INVALID_LENGTH)
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

    @NotNull(message = ErrorMessagesBg.INVALID_PRICE)
    @Digits(integer = 6, fraction = 2, message = ErrorMessagesBg.INVALID_PRICE)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Size(max = 6)
    public List<MultipartFile> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<MultipartFile> pictures) {
        this.pictures = pictures;
    }
}
