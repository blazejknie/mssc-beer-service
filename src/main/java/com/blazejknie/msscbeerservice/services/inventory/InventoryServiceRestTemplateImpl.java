package com.blazejknie.msscbeerservice.services.inventory;

import com.blazejknie.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("!local_discovery")
@Slf4j
@ConfigurationProperties(prefix = "blazej.brewery", ignoreUnknownFields = false)
@Component
public class InventoryServiceRestTemplateImpl implements InventoryService {

    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public InventoryServiceRestTemplateImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    @Override
    public Integer getOnhandInventory(UUID beerId) {
        log.debug("Calling Inventory Service");
        ResponseEntity<List<BeerInventoryDto>> responseEntity = restTemplate.exchange(
                beerInventoryServiceHost + INVENTORY_PATH, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<BeerInventoryDto>>() {
                }, (Object) beerId);

        return Objects.requireNonNull(
                responseEntity.getBody().stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum());
    }
}
