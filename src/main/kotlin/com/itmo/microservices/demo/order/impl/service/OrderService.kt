package com.itmo.microservices.demo.order.impl.service

import com.itmo.microservices.demo.order.api.dto.BookingDto
import com.itmo.microservices.demo.order.api.dto.OrderCatalogItemDto
import com.itmo.microservices.demo.order.api.dto.OrderDto
import com.itmo.microservices.demo.order.api.dto.OrderItemDto
import com.itmo.microservices.demo.order.impl.dao.OrderCatalogItemRepository
import com.itmo.microservices.demo.order.impl.dao.OrderItemRepository
import com.itmo.microservices.demo.order.impl.dao.OrderRepository
import com.itmo.microservices.demo.order.impl.entity.OrderCatalogItemEntity
import com.itmo.microservices.demo.order.impl.entity.OrderItemEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.io.DataOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.sql.Timestamp
import java.util.*
import java.util.stream.Collectors
import java.util.stream.Stream

@Service
class OrderService @Autowired constructor(
    private val repository: OrderRepository,
    private val catalogItemRepository: OrderCatalogItemRepository,
    private val orderItemRepository: OrderItemRepository
) :
    IOrderService {
    override fun createOrder(): OrderDto? {
        return OrderDto()
    }

    override fun getOrderById(uuid: UUID?): OrderDto? {
        val orderEntity = repository.getById(uuid!!)

        // entity.CatalogItem -> dto.CatalogItem
        val dtoItems = orderEntity.catalogItems!!.stream()
            .map<Any> { item: OrderCatalogItemEntity ->
                OrderCatalogItemDto(
                    item.uuid, item.title, item.description,
                    item.price, item.amount
                )
            }.collect(Collectors.toList<Any>())

        // List<dto.CatalogItem> -> Map<OrderItem, Integer>
        val itemList = dtoItems.stream()
            .collect(
                Collectors.toMap(
                    { item: OrderCatalogItemDto ->
                        OrderItemDto(
                            item.uuid,
                            item.title,
                            item.price
                        )
                    },
                    { item: OrderCatalogItemDto ->
                        item.amount
                    }
                )
            )
        return OrderDto(
            orderEntity.uuid, orderEntity.timeCreated,
            itemList, orderEntity.status, orderEntity.deliveryInfo
        )
    }

    override fun putItemToOrder(orderId: UUID?, itemId: UUID?, amount: Int) {
        val orderDto = getOrderById(orderId)
        val item: OrderItemEntity = orderItemRepository.getById(itemId!!)
        val itemDto = OrderItemDto(item.uuid, item.title, item.price)
        orderDto?.itemList?.set(itemDto, amount)
    }

    @Throws(IOException::class)
    override fun book(orderId: UUID?): BookingDto? {
        val orderDto = getOrderById(orderId)
        val url = URL(API_URL + "warehouse/book")
        sendRequest(orderDto, url)
        return null
    }

    @Throws(IOException::class)
    override fun selectDeliveryTime(orderId: UUID?, seconds: Int) {
        val orderDto = getOrderById(orderId)
        orderDto?.deliveryInfo = Timestamp(seconds.toLong())
        val url = URL(API_URL + "delivery/doDelivery")
        sendRequest(orderDto, url)
    }

    @Throws(IOException::class)
    private fun sendRequest(orderDto: OrderDto?, url: URL) {
        val con = url.openConnection() as HttpURLConnection
        con.requestMethod = "POST"
        val parameters: MutableMap<String, String> = HashMap()
        parameters["order"] = orderDto.toString()
        con.doOutput = true
        val out = DataOutputStream(con.outputStream)
        out.writeBytes(getParamsString(parameters))
        out.flush()
        out.close()
    }

    companion object {
        private const val API_URL = "http://77.234.215.138:30019/api/"
    }
}

fun getParamsString(params: MutableMap<String, String>): String {
    val result = StringBuilder()
    for (pair in params) {
        result.append(URLEncoder.encode(pair.key, "UTF-8"))
        result.append("=")
        result.append(URLEncoder.encode(pair.value, "UTF-8"))
        result.append("&")
    }
    val resultString = result.toString()
    return if (resultString.isNotEmpty()) resultString.substring(0, resultString.length - 1) else resultString
}

private fun <T> Stream<T>.collect(toMap: T) {}
