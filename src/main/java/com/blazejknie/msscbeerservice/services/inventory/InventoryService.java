package com.blazejknie.msscbeerservice.services.inventory;

import java.util.UUID;

public interface InventoryService {
    Integer getOnhandInventory(UUID beerId);
}
