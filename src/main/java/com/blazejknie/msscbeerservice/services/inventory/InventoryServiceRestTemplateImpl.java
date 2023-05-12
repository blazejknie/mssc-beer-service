package com.blazejknie.msscbeerservice.services.inventory;

import com.blazejknie.msscbeerservice.services.inventory.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Profile("!localdiscovery")
@Slf4j
@ConfigurationProperties(prefix = "blazej.brewery", ignoreUnknownFields = true)
@Component
public class InventoryServiceRestTemplateImpl implements InventoryService {

    private final RestTemplate restTemplate;

    private String beerInventoryServiceHost;

    public InventoryServiceRestTemplateImpl(RestTemplateBuilder restTemplateBuilder,
                                            @Value("blazej.brewery.inventory-user") String inventoryUser,
                                            @Value("blazej.brewery.inventory-password") String inventoryPassword) {
        this.restTemplate = restTemplateBuilder.basicAuthentication(inventoryUser, inventoryPassword).build();
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
