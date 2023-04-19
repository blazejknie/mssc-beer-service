package com.blazejknie.msscbeerservice.services.brewing;

import com.blazejknie.msscbeerservice.config.JmsConfig;
import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.events.BrewBeerEvent;
import com.blazejknie.msscbeerservice.events.NewInventoryEvent;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import com.blazejknie.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {
    private final BeerRepository beerRepository;
    private final JmsTemplate jmsTemplate;

    @Transactional
    @JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE_NAME)
    public void listen(BrewBeerEvent event) {
        BeerDto beerDto = event.getBeerDto();

        Beer beer = beerRepository.findByUpc(beerDto.getUpc());

        beerDto.setQuantityOnHand(beer.getQuantityToBrew());

        NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);

        log.debug("Brewed Beer: " + beer.getMinOnHand() + " : QOH" + beerDto.getQuantityOnHand());

        jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE_NAME, newInventoryEvent);
    }
}
