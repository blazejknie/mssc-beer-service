package com.blazejknie.msscbeerservice.bootstrap;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final String BEER_1_UPC = "0245464256436";
    public static final String BEER_2_UPC = "0245464256436";
    public static final String BEER_3_UPC = "0245464256436";

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeersObjects();
    }

    private void loadBeersObjects() {
        if (beerRepository.count() == 0) {
            beerRepository.save(Beer.builder()
                                    .beerName("Mango Bobs")
                                    .beerStyle("IPA")
                                    .quantityToBrew(200)
                                    .upc(BEER_1_UPC)
                                    .price(new BigDecimal("11.95"))
                                    .build());

            beerRepository.save(Beer.builder()
                                    .beerName("Galaxy Cat")
                                    .beerStyle("PALE_ALE")
                                    .quantityToBrew(100)
                                    .upc(BEER_2_UPC)
                                    .price(new BigDecimal("9.95"))
                                    .build());

            beerRepository.save(Beer.builder()
                                    .beerName("No Hammers On The Bar")
                                    .beerStyle("PALE_ALE")
                                    .quantityToBrew(100)
                                    .upc(BEER_3_UPC)
                                    .price(new BigDecimal("9.95"))
                                    .build());
        }
    }
}
