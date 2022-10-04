package com.blazejknie.msscbeerservice.web.controller;

import com.blazejknie.msscbeerservice.web.model.BeerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> fetchBeerById(@PathVariable("beerId") UUID beerId) {
        return ResponseEntity.ok(BeerDto.builder().build());
    }

    @PostMapping
    public ResponseEntity<Void> saveNewBeer(@RequestBody BeerDto beerDto) {
        beerDto.setId(UUID.randomUUID());
        return ResponseEntity.created(URI.create("/api/v1/beer/" + beerDto.getId())).build();
    }

    @PutMapping("/{beerId}")
    public ResponseEntity<Void> updateBeerById(@PathVariable("beerId") UUID beerId, @RequestBody BeerDto beerDto) {
        return ResponseEntity.noContent().build();
    }

}
