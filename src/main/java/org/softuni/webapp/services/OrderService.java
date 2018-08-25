package org.softuni.webapp.services;

import org.softuni.webapp.domain.models.view.OrderListModel;

import java.util.List;

public interface OrderService {
    List<OrderListModel> findAllAsListModels();

    void createOrUpdateOrder(String orderedItemId, String customerId);
}
