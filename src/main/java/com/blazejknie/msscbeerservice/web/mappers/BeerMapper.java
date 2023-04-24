package com.blazejknie.msscbeerservice.web.mappers;

import com.blazejknie.msscbeerservice.domain.Beer;
import guru.sfg.brewery.model.BeerDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
    Beer dtoToBeer(BeerDto beerDto);

    BeerDto beerToDtoWithInventory(Beer beer);

    BeerDto beerToDto(Beer beer);
}
