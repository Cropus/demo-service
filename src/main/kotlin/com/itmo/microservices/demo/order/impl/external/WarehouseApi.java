package com.itmo.microservices.demo.order.impl.external;

import com.itmo.microservices.demo.order.api.dto.OrderDto;
import com.itmo.microservices.demo.order.api.external.IWarehouseApi;
import com.itmo.microservices.demo.warehouse.api.model.ItemQuantityRequestDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

public class WarehouseApi implements IWarehouseApi {

    private static final String API_URL = "http://77.234.215.138:30019/api/warehouse/book";

    @Override
    public ResponseEntity<Set> book(OrderDto orderDto) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        Map<String, List<ItemQuantityRequestDTO>> parameters = new HashMap<>();
        List<ItemQuantityRequestDTO> itemList = orderDto.getItemList()
                .stream()
                .map(orderItem ->
                        new ItemQuantityRequestDTO(orderItem.getCatalogItemId(),
                                orderItem.getAmount()))
                .collect(Collectors.toList());
        parameters.put("itemList", itemList);

        HttpEntity<Map<String, List<ItemQuantityRequestDTO>>> request = new HttpEntity<>(parameters, headers);
        return restTemplate.postForEntity(API_URL, request, Set.class);
    }
}
