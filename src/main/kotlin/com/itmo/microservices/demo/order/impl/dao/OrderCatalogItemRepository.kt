package com.itmo.microservices.demo.order.impl.dao

import com.itmo.microservices.demo.order.api.dto.OrderCatalogItemDto
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface OrderCatalogItemRepository : JpaRepository<OrderCatalogItemDto?, UUID?> {
}