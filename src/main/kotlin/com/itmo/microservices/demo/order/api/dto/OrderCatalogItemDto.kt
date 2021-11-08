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
class OrderCatalogItemDto(uuid: UUID?, title: String?, description: String?, price: Int, amount: Int) {
    val uuid: UUID? = null
    val title: String? = null
    val description: String? = null
    val price: Int = 0
    val amount: Int = 0
}