package com.blazejknie.msscbeerservice.bootstrap;

import com.blazejknie.msscbeerservice.domain.Beer;
import com.blazejknie.msscbeerservice.repositories.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class BeerLoader implements CommandLineRunner {

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
                                    .upc(3000000001L)
                                    .price(new BigDecimal("11.95"))
                                    .build());

            beerRepository.save(Beer.builder()
                                    .beerName("Galaxy Cat")
                                    .beerStyle("PALE_ALE")
                                    .quantityToBrew(100)
                                    .upc(3000000002L)
                                    .price(new BigDecimal("9.95"))
                                    .build());
        }
    }
}
