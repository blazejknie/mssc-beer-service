package com.blazejknie.msscbeerservice.services;

import com.blazejknie.msscbeerservice.web.model.BeerDto;
import com.blazejknie.msscbeerservice.web.model.BeerPagedList;
import org.springframework.data.domain.PageRequest;

import java.util.UUID;

public interface BeerService {
    BeerDto getById(UUID id, Boolean showOnHand);

    BeerDto save(BeerDto beerDto);

    BeerDto update(UUID beerId, BeerDto beerDto);

    BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest, Boolean showOnHand);

    BeerDto fetchBeerByUpc(String upc, Boolean showInventoryOnHand);
}
