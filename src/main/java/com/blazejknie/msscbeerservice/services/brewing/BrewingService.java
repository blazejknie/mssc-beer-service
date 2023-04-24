package com.blazejknie.msscbeerservice.services.brewing;

import com.blazejknie.msscbeerservice.config.JmsConfig;
import com.blazejknie.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.events.BrewBeerEvent;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import com.blazejknie.msscbeerservice.services.inventory.InventoryService;
import com.blazejknie.msscbeerservice.web.mappers.BeerMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BrewingService {
    private static final int FIVE_SECONDS = 5000;
    private final BeerRepository beerRepository;
    private final InventoryService inventoryService;
    private final JmsTemplate jmsTemplate;
    private final BeerMapper beerMapper;

    @Scheduled(fixedRate = FIVE_SECONDS)
    public void checkForLowInventory() {
        List<Beer> beers = beerRepository.findAll();

        beers.forEach(beer -> {
            Integer invQOH = inventoryService.getOnhandInventory(beer.getId());

            log.debug(String.format("%s:", beer.getBeerName()));
            log.debug("Min Onhand is " + beer.getMinOnHand());
            log.debug("Inventory is: " + invQOH);

            if (invQOH < beer.getMinOnHand()) {
                BeerDto beerDto = beerMapper.beerToDto(beer);
                BrewBeerEvent message = new BrewBeerEvent(beerDto);
                jmsTemplate.convertAndSend(JmsConfig.BREWING_REQUEST_QUEUE_NAME, message);
            }
        });
    }
}
