package org.softuni.webapp.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.webapp.constants.AppConstants;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "items_in_order")
public class OrderItem implements Serializable {
    private String id;

    private String orderedItemId;

    private Integer orderedItemQuantity;

    public OrderItem() {
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

    @NotNull(message = AppConstants.ORDERED_ITEM_ID_CANNOT_BE_NULL)
    @Column(name = "ordered_item_id", unique = true)
    public String getOrderedItemId() {
        return this.orderedItemId;
    }

    public void setOrderedItemId(String orderedItemId) {
        this.orderedItemId = orderedItemId;
    }

    @NotNull(message = AppConstants._QUANTITY_CANNOT_BE_NULL)
    @Column(name = "ordered_item_quantity")
    @Min(value = 1, message = AppConstants.MINIMUM_QUANTITY)
    public Integer getOrderedItemQuantity() {
        return this.orderedItemQuantity;
    }

    public void setOrderedItemQuantity(Integer orderedItemQuantity) {
        this.orderedItemQuantity = orderedItemQuantity;
    }
}
