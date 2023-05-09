package com.blazejknie.msscbeerservice.services.inventory;

import com.blazejknie.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Profile("local_discovery")
@Service
public class BeerInventoryFeignService implements InventoryService {

    private final InventoryServiceFeignClient inventoryServiceFeignClient;

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        log.debug("Calling Inventory Service - Beer Id: " + beerId);

        ResponseEntity<List<BeerInventoryDto>> response = inventoryServiceFeignClient.getOnhandInventory(beerId);

        Integer onHand = Objects.requireNonNull(response.getBody())
                .stream()
                .mapToInt(BeerInventoryDto::getQuantityOnHand)
                .sum();

        log.debug("BeerId: " + beerId + " On Hand is: " + onHand);

        return onHand;
    }
}
