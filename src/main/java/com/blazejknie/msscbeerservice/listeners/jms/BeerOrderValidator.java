package com.blazejknie.msscbeerservice.listeners.jms;

import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import guru.sfg.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
@RequiredArgsConstructor
public class BeerOrderValidator {
    private final BeerRepository beerRepository;

    public Boolean validate(BeerOrderDto beerOrderDto) {
        AtomicInteger beersNotFound = new AtomicInteger();

        beerOrderDto.getBeerOrderLines().forEach(order -> {
            if (!beerRepository.existsById(order.getBeerId())) {
                beersNotFound.incrementAndGet();
            }
        });

        return beersNotFound.intValue() == 0;
    }

}
