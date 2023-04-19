package com.blazejknie.msscbeerservice.events;

import com.blazejknie.msscbeerservice.web.model.BeerDto;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {

    static final long serialVersionUID = 226313508554078494L;

    private final BeerDto beerDto;
}
