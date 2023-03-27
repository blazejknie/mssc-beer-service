package com.blazejknie.msscbeerservice.web.controller;

import com.blazejknie.msscbeerservice.services.BeerService;
import com.blazejknie.msscbeerservice.web.model.BeerDto;
import com.blazejknie.msscbeerservice.web.model.BeerPagedList;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private static Integer DEFAULT_PAGE_SIZE = 25;
    private static Integer DEFAULT_PAGE_NUMBER = 0;

    private final BeerService beerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value = "pageNumber",required = false) Integer pageNumber,
                                                   @RequestParam(value = "pageSize", required = false) Integer pageSize,
                                                   @RequestParam(value = "beerName", required = false) String beerName,
                                                   @RequestParam(value = "beerStyle", required = false) String beerStyle,
                                                   @RequestParam(value = "showInventoryOnHand", required = false) Boolean showOnHand) {
        if (pageNumber == null || pageNumber < 0) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if (pageSize == null || pageSize < 1) {
            pageSize = DEFAULT_PAGE_SIZE;
        }

        if (showOnHand == null) {
            showOnHand = false;
        }

        return ResponseEntity.ok(beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize),showOnHand));
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> fetchBeerById(@PathVariable("beerId") UUID beerId,
                                                 @RequestParam(value = "showOnHand", required = false) Boolean showOnHand) {
        if (showOnHand == null) {
            showOnHand = false;
        }
        return ResponseEntity.ok(beerService.getById(beerId, showOnHand));
    }

    @PostMapping
    public ResponseEntity<Void> saveNewBeer(@Valid @RequestBody BeerDto beerDto) {
         BeerDto savedBeer = beerService.save(beerDto);
        return ResponseEntity.created(URI.create("/api/v1/beer/" + savedBeer.getId())).build();
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto) {
        BeerDto updated = beerService.update(beerId, beerDto);
        return ResponseEntity.created(URI.create("/api/v1/beer/" + updated.getId())).build();
    }

}
