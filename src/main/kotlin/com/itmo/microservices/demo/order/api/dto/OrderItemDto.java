package com.itmo.microservices.demo.order.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private UUID uuid;
    private String title;
    private int price;

    public UUID getUuid() {
        return uuid;
    }

    public String getTitle() {
        return title;
    }

    public int getPrice() {
        return price;
    }
}
