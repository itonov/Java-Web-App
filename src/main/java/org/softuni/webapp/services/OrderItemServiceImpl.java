package org.softuni.webapp.services;

import org.softuni.webapp.domain.entities.OrderItem;
import org.softuni.webapp.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private OrderItemRepository orderItemRepository;

    @Autowired
    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public void saveOrderItem(OrderItem itemToAdd) {
        this.orderItemRepository.save(itemToAdd);
    }

    @Override
    public Set<OrderItem> findAll() {
        return this.orderItemRepository.findAll().stream().collect(Collectors.toSet());
    }
}
