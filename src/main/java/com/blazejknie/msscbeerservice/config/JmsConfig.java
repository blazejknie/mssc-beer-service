package com.blazejknie.msscbeerservice.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@AllArgsConstructor
public class JmsConfig {

    public static final String BREWING_REQUEST_QUEUE_NAME = "brewing-request";
    public static final String NEW_INVENTORY_QUEUE_NAME = "new-inventory";
    public static final String VALIDATE_ORDER_QUEUE_NAME = "validate-order";
    public static final String VALIDATE_ORDER_RESULT_QUEUE_NAME = "validate-order-result";

    private ObjectMapper objectMapper;

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        converter.setObjectMapper(objectMapper);
        return converter;
    }

}
