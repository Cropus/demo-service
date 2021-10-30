package com.itmo.microservices.demo.order.api.controller;

import com.itmo.microservices.demo.order.api.dto.Order;
import com.itmo.microservices.demo.order.impl.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService service;

    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(
            summary = "Create order",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void createOrder(@RequestBody Order order) {
        service.createOrder(order);
    }

    @GetMapping("/{order_id}")
    @Operation(
            summary = "Get order",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void getOrder(@PathVariable("order_id") UUID uuid) {
        service.getOrderById(uuid);
    }

    @PutMapping("/{order_id}/items/{item_id}?amount={amount}")
    @Operation(
            summary = "Put the item in the cart",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Something went wrong", responseCode = "400")},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void updateOrder(@PathVariable("order_id") UUID orderId, @PathVariable("item_id") UUID itemId,
                            @PathVariable("amount") int amount) {
        service.updateOrder(orderId, itemId, amount);
    }

    @PostMapping("/{order_id}/bookings")
    @Operation(
            summary = "Book",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void bookOrder(@PathVariable("order_id") UUID orderId) {
        service.book(orderId);
    }

    @PostMapping("/{order_id}/delivery?slot={slot_in_sec}")
    @Operation(
            summary = "Deliveru time selection",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Something went wrong", responseCode = "400")},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void selectDeliveryTime(@PathVariable("order_id") UUID orderId,
                                   @PathVariable("slot_in_sec") int seconds) {
        service.selectDeliveryTime(orderId, seconds);
    }
}
