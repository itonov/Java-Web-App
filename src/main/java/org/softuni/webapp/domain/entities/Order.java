package org.softuni.webapp.domain.entities;

import org.hibernate.annotations.GenericGenerator;
import org.softuni.webapp.constants.AppConstants;
import org.softuni.webapp.domain.entities.enums.OrderStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order implements Serializable {
    private String id;

    private Set<OrderItem> orderedItems;

    private OrderStatus status;

    public Order() {
        this.orderedItems = new HashSet<>();
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

    @Size(max = 10, message = AppConstants.ITEMS_SIZE_TOO_BIG)
    @ManyToMany(targetEntity = Product.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "orders_items",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    public Set<OrderItem> getOrderedItems() {
        return this.orderedItems;
    }

    public void setOrderedItems(Set<OrderItem> orderedItems) {
        this.orderedItems = orderedItems;
    }

    public void addOrderedItem(OrderItem orderItem) {
        this.orderedItems.add(orderItem);
    }

    @NotNull(message = AppConstants.STATUS_CANNOT_BE_NULL)
    @Column(name = "order_status")
    @Enumerated(EnumType.STRING)
    public OrderStatus getStatus() {
        return this.status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
