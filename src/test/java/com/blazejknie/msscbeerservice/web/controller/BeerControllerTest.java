package com.blazejknie.msscbeerservice.web.controller;

import com.blazejknie.msscbeerservice.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
class BeerControllerTest {


    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(BeerController.class).build();
    }

    @Test
    void fetchBeerById() throws Exception {
        mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk());
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto build = BeerDto.builder().build();
        String beerAsString = objectMapper.writeValueAsString(build);

        mockMvc.perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerAsString))
               .andExpect(status().isCreated());

    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto build = BeerDto.builder().build();
        String beerAsString = objectMapper.writeValueAsString(build);

        mockMvc.perform(
                       put("/api/v1/beer/" + UUID.randomUUID()).contentType(MediaType.APPLICATION_JSON).content(beerAsString))
               .andExpect(status().isNoContent());
    }
}