package com.blazejknie.msscbeerservice.services.inventory;

import java.util.UUID;

public interface InventoryService {

    String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";

    Integer getOnhandInventory(UUID beerId);
}
