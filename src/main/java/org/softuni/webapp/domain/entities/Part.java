package org.softuni.webapp.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.webapp.constants.AppConstants;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parts")
public class Part implements Serializable {
    private String id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer quantity;

    private List<Picture> pictures;

    public Part() {
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

    @NotNull(message = AppConstants.NAME_CANNOT_BE_NULL)
    @Size(min = 2, max = 10, message = AppConstants.NAME_ALLOWED_SIZE)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Digits(integer = 4, fraction = 2)
    public BigDecimal getPrice() {
        return this.price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @NotNull(message = AppConstants._QUANTITY_CANNOT_BE_NULL)
    @Min(value = 1L, message = AppConstants.MINIMUM_QUANTITY)
    public Integer getQuantity() {
        return this.quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Size(max = 6, message = AppConstants.PICTURES_MAX_COUNT)
    @OneToMany(cascade = CascadeType.ALL, targetEntity = Picture.class)
    public List<Picture> getPictures() {
        return this.pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
