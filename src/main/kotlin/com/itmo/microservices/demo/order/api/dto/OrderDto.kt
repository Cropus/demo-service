package com.itmo.microservices.demo.order.api.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

@Data
@Builder
@AllArgsConstructor
class OrderDto() {
    constructor(
        uuid: UUID?,
        timeCreated: LocalDateTime?,
        itemList: Unit,
        status: OrderStatus?,
        deliveryInfo: Timestamp?
    ) : this()

    val uuid: UUID = UUID.randomUUID()
    val timeCreated: LocalDateTime = LocalDateTime.now()
    val itemList: MutableMap<OrderItemDto, Int>
    val status: OrderStatus
    var deliveryInfo: Timestamp
        get() {
            TODO()
        }
        set(value) {}

    init {
        itemList = HashMap()
        status = OrderStatus.COLLECTING
        deliveryInfo = Timestamp(0)
    }
}