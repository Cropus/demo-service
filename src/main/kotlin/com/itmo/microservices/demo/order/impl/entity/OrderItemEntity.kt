package com.itmo.microservices.demo.order.impl.entity

import lombok.Getter
import lombok.RequiredArgsConstructor
import lombok.Setter
import lombok.ToString
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
class OrderItemEntity {
    @Id
    @GeneratedValue
    val uuid: UUID? = null
    val title: String? = null
    val price = 0
}