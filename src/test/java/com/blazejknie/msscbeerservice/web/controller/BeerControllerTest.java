package com.blazejknie.msscbeerservice.web.controller;

import com.blazejknie.msscbeerservice.bootstrap.BeerLoader;
import com.blazejknie.msscbeerservice.services.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith({RestDocumentationExtension.class})
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "dev.springframework.blazej", uriPort = 80)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    @BeforeEach
    void setUp(WebApplicationContext webApplicationContext, RestDocumentationContextProvider restDocumentation) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(restDocumentation))
                .build();
    }


    @Test
    void fetchBeerById() throws Exception {

        BeerDto validBeer = getValidBeer();
        String asString = objectMapper.writeValueAsString(validBeer);
        given(beerService.getById(any(UUID.class), anyBoolean())).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString()).param("isCold", "yes")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(parameterWithName("beerId").description("UUID for dosired beer to get")),
                        requestParameters(parameterWithName("isCold").description(
                                "filter respons to get cold or not cold beers"))));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto build = getValidBeer();
        String beerAsString = objectMapper.writeValueAsString(build);

        given(beerService.save(any(BeerDto.class))).willReturn(build);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer").contentType(MediaType.APPLICATION_JSON).content(beerAsString))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(fieldWithPath("id").ignored(), fieldWithPath("version").ignored(),
                                fields.withPath("createdDate").ignored(), fieldWithPath("lastUpdatedDate").ignored(),
                                fields.withPath("beerName").description("Name for beer"),
                                fields.withPath("beerStyle").description("Style of beer"),
                                fields.withPath("upc").description("Unique number for product"),
                                fields.withPath("price").description("Basic Price of beer"),
                                fields.withPath("quantityOnHand").ignored())));

    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto build = getValidBeer();
        String beerAsString = objectMapper.writeValueAsString(build);

        given(beerService.update(any(UUID.class), any(BeerDto.class))).willReturn(build);
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);


        mockMvc.perform(
                        put("/api/v1/beer/{beerId}", UUID.randomUUID().toString()).contentType(MediaType.APPLICATION_JSON)
                                .content(beerAsString))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-update",
                        pathParameters(parameterWithName("beerId").description("UUID for dosired beer to get")),
                        requestFields(fieldWithPath("id").ignored(), fieldWithPath("version").ignored(),
                                fields.withPath("createdDate").ignored(), fieldWithPath("lastUpdatedDate").ignored(),
                                fields.withPath("beerName").description("Name for beer"),
                                fields.withPath("beerStyle").description("Style of beer"),
                                fields.withPath("upc").description("Unique number for product"),
                                fields.withPath("price").description("Basic Price of beer"),
                                fields.withPath("quantityOnHand").ignored())));
        ;
    }

    BeerDto getValidBeer() {
        return BeerDto.builder()
                .beerName("Valid Beer")
                .beerStyle(BeerStyle.ALE.name())
                .price(new BigDecimal("3.00"))
                .upc(BeerLoader.BEER_1_UPC)
                .build();
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ". ")));
        }
    }
}