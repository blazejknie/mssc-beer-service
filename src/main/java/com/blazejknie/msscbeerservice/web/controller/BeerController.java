package com.blazejknie.msscbeerservice.web.controller;

import com.blazejknie.msscbeerservice.services.BeerService;
import com.blazejknie.msscbeerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.util.UUID;
@RequiredArgsConstructor
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> fetchBeerById(@PathVariable("beerId") UUID beerId) {
        return ResponseEntity.ok(beerService.getById(beerId));
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
