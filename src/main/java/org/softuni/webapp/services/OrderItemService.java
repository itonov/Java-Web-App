package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.OrderItem;

import java.util.Set;

public interface OrderItemService {
    void saveOrderItem(OrderItem itemToAdd);

    Set<OrderItem> findAll();
}
