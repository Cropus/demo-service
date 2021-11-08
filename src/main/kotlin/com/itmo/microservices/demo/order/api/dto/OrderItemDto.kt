package com.itmo.microservices.demo.order.api.dto

import lombok.AllArgsConstructor
import lombok.Builder
import lombok.Data
import lombok.NoArgsConstructor
import java.util.*

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class OrderItemDto(uuid: UUID?, title: String?, price: Int) {
    val uuid: UUID? = null
    val title: String? = null
    val price: Int = 0
}