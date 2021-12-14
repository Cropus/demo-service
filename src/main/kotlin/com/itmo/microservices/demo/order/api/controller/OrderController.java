package com.itmo.microservices.demo.order.api.controller;

import com.itmo.microservices.demo.order.api.exception.BookingException;
import com.itmo.microservices.demo.order.api.dto.BookingDto;
import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.exception.OrderIsNotExistException;
import com.itmo.microservices.demo.order.impl.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
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
    public OrderDto createOrder() {
        return service.createOrder();
    }

    @GetMapping("/{order_id}")
    @Operation(
            summary = "Get order",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Order not found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<OrderDto> getOrder(@PathVariable("order_id") UUID uuid) {
        OrderDto order;
        try {
            order = service.getOrderById(uuid);
        }
        catch (OrderIsNotExistException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/{order_id}/items/{item_id}")
    @Operation(
            summary = "Put the item in the cart",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Something went wrong", responseCode = "400")},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public void updateOrder(@PathVariable("order_id") UUID orderId,
                            @PathVariable("item_id") UUID itemId,
                            @RequestParam(name = "amount") int amount) {
        service.putItemToOrder(orderId, itemId, amount);
    }

    @PostMapping("/{order_id}/bookings")
    @Operation(
            summary = "Book",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Order not found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<BookingDto> bookOrder(@PathVariable("order_id") UUID orderId) {
        BookingDto booking;
        try {
            booking = service.bookOrder(orderId);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (BookingException e) {
            return new ResponseEntity<>(null, HttpStatus.REQUEST_TIMEOUT);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @PostMapping("/{order_id}/delivery")
    @Operation(
            summary = "Delivery time selection",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {@Content}),
                    @ApiResponse(description = "Order not found", responseCode = "404", content = {@Content}),
                    @ApiResponse(description = "Something went wrong", responseCode = "400", content = {@Content})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    public ResponseEntity<BookingDto> selectDeliveryTime(@PathVariable("order_id") UUID orderId,
                                                         @RequestParam(name = "slot_in_sec") int seconds) {
        BookingDto booking;
        try {
            booking = service.selectDeliveryTime(orderId, seconds);
        }
        catch (EntityNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(booking, HttpStatus.OK);
    }

    @GetMapping("/suka")
    @Operation(security = {@SecurityRequirement(name = "bearerAuth")})
    public void test(@RequestParam(required = false) UUID orderId) {
        service.startPayment(orderId);
    }
}
