package com.blazejknie.msscbeerservice.web.mappers;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.services.inventory.InventoryService;
import guru.sfg.brewery.model.events.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class BeerMapperDecorator implements BeerMapper {

    InventoryService inventoryService;


    BeerMapper beerMapper;

    @Autowired
    public void setInventoryService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @Autowired
    public void setBeerMapper(BeerMapper beerMapper) {
        this.beerMapper = beerMapper;
    }

    @Override
    public Beer dtoToBeer(BeerDto beerDto) {
        return beerMapper.dtoToBeer(beerDto);
    }

    @Override
    public BeerDto beerToDtoWithInventory(Beer beer) {
        BeerDto beerDto = beerMapper.beerToDtoWithInventory(beer);
        beerDto.setQuantityOnHand(inventoryService.getOnhandInventory(beer.getId()));
        return beerDto;
    }

    @Override
    public BeerDto beerToDto(Beer beer) {
        return beerMapper.beerToDto(beer);
    }
}
