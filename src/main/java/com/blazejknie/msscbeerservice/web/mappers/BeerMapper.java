package com.blazejknie.msscbeerservice.web.mappers;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
    Beer dtoToBeer(BeerDto beerDto);

    BeerDto beerToDto(Beer beer);
}
