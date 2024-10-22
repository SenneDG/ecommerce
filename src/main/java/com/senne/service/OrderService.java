package com.senne.service;

import java.util.List;
import java.util.Set;

import com.senne.domain.OrderStatus;
import com.senne.modal.Address;
import com.senne.modal.Cart;
import com.senne.modal.Order;
import com.senne.modal.OrderItem;
import com.senne.modal.User;

public interface OrderService {

    Set<Order> createOrder(User user, Address shippingAddress, Cart cart);

    Order findOrderById(Long orderId) throws Exception;

    List<Order> usersOrderHistory(Long userId);

    List<Order> sellersOrder(Long sellerId);

    Order updateOrderStatus(Long orderId, OrderStatus status) throws Exception;

    Order cancelOrder(Long orderId, User user) throws Exception;

    OrderItem getOrderItemById(Long orderItemId) throws Exception;
}
