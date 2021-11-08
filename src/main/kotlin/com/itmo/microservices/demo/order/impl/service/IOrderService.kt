package com.itmo.microservices.demo.order.impl.service

import com.itmo.microservices.demo.order.api.dto.BookingDto
import com.itmo.microservices.demo.order.api.dto.OrderDto
import java.util.*

interface IOrderService {
    fun selectDeliveryTime(orderId: UUID?, seconds: Int)
    fun book(orderId: UUID?): BookingDto?
    fun putItemToOrder(orderId: UUID?, itemId: UUID?, amount: Int)
    fun getOrderById(uuid: UUID?): OrderDto?
    fun createOrder(): OrderDto?
}