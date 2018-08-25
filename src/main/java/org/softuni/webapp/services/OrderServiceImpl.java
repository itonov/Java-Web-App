package org.softuni.webapp.services;

import org.modelmapper.ModelMapper;
import org.softuni.webapp.domain.entities.Order;
import org.softuni.webapp.domain.entities.OrderItem;
import org.softuni.webapp.domain.entities.User;
import org.softuni.webapp.domain.entities.enums.OrderStatus;
import org.softuni.webapp.domain.models.view.OrderListModel;
import org.softuni.webapp.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderItemService orderItemService;

    private UserService userService;

    private OrderRepository orderRepository;

    private ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderItemService orderItemService,
                            UserService userService,
                            OrderRepository orderRepository,
                            ModelMapper modelMapper) {
        this.orderItemService = orderItemService;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    private boolean updateQuantityIfItemExists(Set<OrderItem> orderedItems, String idToCheck) {
        for (OrderItem item : orderedItems) {
            if (item.getOrderedItemId().equals(idToCheck)) {
                item.setOrderedItemQuantity(item.getOrderedItemQuantity() + 1);

                return true;
            }
        }

        return false;
    }

    private void addItemToOrder(Order order, String itemId) {
        OrderItem newOrderItem = new OrderItem();

        newOrderItem.setOrderedItemId(itemId);
        newOrderItem.setOrderedItemQuantity(1);

        order.addOrderedItem(newOrderItem);
    }

    @Override
    public List<OrderListModel> findAllAsListModels() {
        List<Order> ordersFromDb = this.orderRepository.findAll();

        List<OrderListModel> models = ordersFromDb.stream()
                .map(o -> this.modelMapper.map(o, OrderListModel.class))
                .collect(Collectors.toList());

        return models;
    }

    @Override
    public void createOrUpdateOrder(String orderedItemId, String customerId) {
        User client = this.userService.findById(customerId);

        Set<Order> allOrdersForUser = client.getOrders();

//        if (!allOrdersForUser.isEmpty()) {
//            Order orderToUpdate = allOrdersForUser.stream()
//                    .filter(o -> o.getStatus().equals(OrderStatus.PREPARING))
//                    .findFirst()
//                    .orElse(null);
//
//            if (orderToUpdate != null && orderToUpdate.getOrderedItems().size() < 10) {
//                Set<OrderItem> currentOrderedItems = orderToUpdate.getOrderedItems();
//
//                String itemCandidateId = "";
//
//                if (productCandidate != null) {
//                    itemCandidateId = productCandidate.getId();
//                }
//
//                if (partCandidate != null) {
//                    itemCandidateId = partCandidate.getId();
//                }
//
//                boolean itemAlreadyExists;
//
//                if (!itemCandidateId.equalsIgnoreCase("")) {
//                    itemAlreadyExists = this.updateQuantityIfItemExists(currentOrderedItems, itemCandidateId);
//
//                    if (!itemAlreadyExists) {
//                        this.addItemToOrder(orderToUpdate, itemCandidateId);
//                        this.userService.saveUser(client);
//                    }
//
//                    return;
//                }
//            }
//        }

        OrderItem itemToAdd = new OrderItem();
        itemToAdd.setOrderedItemId(orderedItemId);
        itemToAdd.setOrderedItemQuantity(1);

        this.orderItemService.saveOrderItem(itemToAdd);

        Order newOrder = new Order();
        Set<OrderItem> newItems = new HashSet<>();
        newItems.add(this.orderItemService.findAll().stream()
                .filter(i -> i.getOrderedItemId()
                        .equals(orderedItemId))
                .findFirst()
                .orElse(null));

        //TODO: FINISH THIS BASTARD
        newOrder.setStatus(OrderStatus.PREPARING);
        newOrder.setOrderedItems(newItems);
        this.orderRepository.save(newOrder);

        client.addOrder(newOrder);
        this.userService.saveUser(client);
    }
}
