package com.blazejknie.msscbeerservice.services;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import com.blazejknie.msscbeerservice.web.controller.NotFoundException;
import com.blazejknie.msscbeerservice.web.mappers.BeerMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Cacheable(cacheNames = "beerCache", condition = "#showOnHand == false", key = "#id")
    @Override
    public BeerDto getById(UUID id, Boolean showOnHand) {
        Beer beer = repository.findById(id).orElseThrow(() -> new NotFoundException("No beer with id: " + id));
        return this.mapBeerToDto(beer, showOnHand);
    }

    @Override
    public BeerDto save(BeerDto beerDto) {
        Beer savedBeer = repository.save(mapper.dtoToBeer(beerDto));
        return mapper.beerToDto(savedBeer);
    }

    @Override
    public BeerDto update(UUID beerId, BeerDto beerDto) {
        Beer beer = repository.findById(beerId)
                .orElseThrow((() -> new NotFoundException("No beer with id: " + beerId)));

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return mapper.beerToDto(repository.save(beer));
    }

    @Cacheable(cacheNames = "beerListCache", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerPagedList listBeers(String beerName, String beerStyle, PageRequest pageRequest, Boolean showInventoryOnHand) {
        Page<Beer> beerPage;
        BeerPagedList beerPagedList;

        if (StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = repository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (StringUtils.hasText(beerName) && !StringUtils.hasText(beerStyle)) {
            beerPage = repository.findAllByBeerName(beerName, pageRequest);
        } else if (!StringUtils.hasText(beerName) && StringUtils.hasText(beerStyle)) {
            beerPage = repository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = repository.findAll(pageRequest);
        }

        beerPagedList = new BeerPagedList(
                beerPage.getContent().stream().map(beer -> mapBeerToDto(beer, showInventoryOnHand)).collect(Collectors.toList()),
                PageRequest.of(beerPage.getPageable().getPageNumber(), beerPage.getPageable().getPageSize()),
                beerPage.getTotalElements());
        return beerPagedList;
    }

    @Cacheable(cacheNames = "beerByUpc", key = "#upc", condition = "#showInventoryOnHand == false ")
    @Override
    public BeerDto fetchBeerByUpc(String upc, Boolean showInventoryOnHand) {
        System.out.println("Calling");
        return mapBeerToDto(repository.findByUpc(upc), showInventoryOnHand);
    }

    private BeerDto mapBeerToDto(Beer beer, boolean showInventory) {
        return showInventory ? mapper.beerToDtoWithInventory(beer) : mapper.beerToDto(beer);
    }
}
