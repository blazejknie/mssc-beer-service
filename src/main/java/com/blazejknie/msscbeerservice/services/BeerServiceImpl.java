package com.blazejknie.msscbeerservice.services;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import com.blazejknie.msscbeerservice.web.controller.NotFoundException;
import com.blazejknie.msscbeerservice.web.mappers.BeerMapper;
import com.blazejknie.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BeerServiceImpl implements BeerService {

    private final BeerRepository repository;
    private final BeerMapper mapper;

    @Override
    public BeerDto getById(UUID id) {
        return mapper.beerToDto(repository.findById(id).orElseThrow(() -> new NotFoundException("No beer with id: " + id)));
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
        beer.setBeerStyle(beerDto.getBeerStyle().name());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return mapper.beerToDto(repository.save(beer));
    }
}
