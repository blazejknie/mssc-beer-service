package com.blazejknie.msscbeerservice.listeners.jms;

import com.blazejknie.msscbeerservice.config.JmsConfig;
import guru.sfg.brewery.model.BeerOrderDto;
import guru.sfg.brewery.model.events.ValidateBeerOrderRequest;
import guru.sfg.brewery.model.events.ValidateBeerOrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidateBeerOrderListener {
    private final BeerOrderValidator beerOrderValidator;
    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.VALIDATE_ORDER_QUEUE_NAME)
    public void validate(ValidateBeerOrderRequest request) {
        BeerOrderDto beerOrderDto = request.getBeerOrderDto();
        Boolean isValid = beerOrderValidator.validate(beerOrderDto);

        jmsTemplate.convertAndSend(JmsConfig.VALIDATE_ORDER_RESULT_QUEUE_NAME,
                ValidateBeerOrderResponse.builder().orderId(beerOrderDto.getId()).isValid(isValid).build());

    }
}
