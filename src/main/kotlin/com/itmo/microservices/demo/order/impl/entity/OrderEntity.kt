package com.itmo.microservices.demo.order.impl.entity

import com.itmo.microservices.demo.order.api.dto.OrderStatus
import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString
import org.hibernate.Hibernate
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
class OrderEntity {
    @Id
    val uuid: UUID? = null
    val timeCreated: LocalDateTime? = null
    val status: OrderStatus? = null
    val deliveryInfo: Timestamp? = null

    @OneToMany
    @ToString.Exclude
    val catalogItems: List<OrderCatalogItemEntity>? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false
        val order = o as OrderEntity
        return uuid != null && uuid == order.uuid
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}