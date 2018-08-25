package org.softuni.webapp.controllers;

import org.softuni.webapp.domain.entities.User;
import org.softuni.webapp.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderController {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders/create/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createOrUpdateOrder(@PathVariable(name = "id") String productId,
                                      @AuthenticationPrincipal User principal) {
        String customerId = principal.getId();

        this.orderService.createOrUpdateOrder(productId, customerId);

        return "redirect:/products/all";
    }
}
