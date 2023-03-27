package com.blazejknie.msscbeerservice.services.inventory;

import com.blazejknie.msscbeerservice.bootstrap.BeerLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class InventoryServiceRestTemplateImplTest {

    @Autowired
    InventoryService inventoryService;

    @Test
    void testGetOnhandInventory() {
        Integer onhandInventory = inventoryService.getOnhandInventory(BeerLoader.BEER_1_UUID);
        System.out.println(onhandInventory);
    }
}