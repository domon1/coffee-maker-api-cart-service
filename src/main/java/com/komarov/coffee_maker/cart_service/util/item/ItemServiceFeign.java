package com.komarov.coffee_maker.cart_service.util.item;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "item-consumer", url = "http://localhost:8082/api/v1/item")
public interface ItemServiceFeign {
    @GetMapping(value = "/{id}")
    ItemDTO findItemById(@PathVariable Long id);
}

