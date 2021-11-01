package com.itmo.microservices.demo.order.impl.service;

import java.util.UUID;

public interface IOrderService {
    Order createOrder(Order order);
    Order getOrderById(UUID orderId);
    void updateOrder (UUID orderId, UUID itemId, int amount);
    Booking book(UUID orderId);
    void selectDeliveryTime(UUID orderId, int seconds);
}
