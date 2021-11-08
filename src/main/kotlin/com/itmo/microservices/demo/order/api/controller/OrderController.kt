package com.itmo.microservices.demo.order.api.controller

import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.impl.service.OrderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.io.IOException
import java.util.*

@RestController
@RequestMapping("/orders")
class OrderController @Autowired constructor(private val service: OrderService) {
    @PostMapping
    @Operation(
        summary = "Create order",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content()]
        ), ApiResponse(description = "Something went wrong", responseCode = "400", content = [Content()])],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun createOrder(): OrderDto? {
        return service.createOrder()
    }

    @GetMapping("/{orderId}")
    @Operation(
        summary = "Get order",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content()]
        ), ApiResponse(description = "Something went wrong", responseCode = "400", content = [Content()])],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun getOrder(@PathVariable("orderId") uuid: UUID?) {
        service.getOrderById(uuid)
    }

    @PutMapping("/{orderId}/items/{itemId}?amount={amount}")
    @Operation(
        summary = "Put the item in the cart",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200"
        ), ApiResponse(description = "Something went wrong", responseCode = "400")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    fun updateOrder(
        @PathVariable("orderId") orderId: UUID?,
        @PathVariable("itemId") itemId: UUID?,
        @PathVariable("amount") amount: Int
    ) {
        service.putItemToOrder(orderId, itemId, amount)
    }

    @PostMapping("/{orderId}/bookings")
    @Operation(
        summary = "Book",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200",
            content = [Content()]
        ), ApiResponse(description = "Something went wrong", responseCode = "400", content = [Content()])],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @Throws(
        IOException::class
    )
    fun bookOrder(@PathVariable("orderId") orderId: UUID?) {
        service.book(orderId)
    }

    @PostMapping("/{orderId}/delivery?slot={slot_in_sec}")
    @Operation(
        summary = "Delivery time selection",
        responses = [ApiResponse(
            description = "OK",
            responseCode = "200"
        ), ApiResponse(description = "Something went wrong", responseCode = "400")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @Throws(
        IOException::class
    )
    fun selectDeliveryTime(
        @PathVariable("orderId") orderId: UUID?,
        @PathVariable("slot_in_sec") seconds: Int
    ) {
        service.selectDeliveryTime(orderId, seconds)
    }
}