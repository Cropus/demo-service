package com.itmo.microservices.demo.order.impl.entity

import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString
import org.hibernate.Hibernate
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
class OrderCatalogItemEntity {
    @Id
    @GeneratedValue
    val uuid: UUID? = null
    val title: String? = null
    val description: String? = null
    val price = 0
    val amount = 0

    @ManyToOne
    private val order: OrderEntity? = null
    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false
        val that = o as OrderCatalogItemEntity
        return uuid != null && uuid == that.uuid
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}