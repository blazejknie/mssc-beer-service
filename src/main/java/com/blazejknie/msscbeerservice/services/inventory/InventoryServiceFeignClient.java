package com.blazejknie.msscbeerservice.services.inventory;

import com.blazejknie.msscbeerservice.services.inventory.model.BeerInventoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "beer-inventory-service", fallback = InventoryServiceFeignClientFailover.class)
public interface InventoryServiceFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = InventoryService.INVENTORY_PATH)
    ResponseEntity<List<BeerInventoryDto>> getOnhandInventory(@PathVariable(name = "beerId") UUID beerId);
}
