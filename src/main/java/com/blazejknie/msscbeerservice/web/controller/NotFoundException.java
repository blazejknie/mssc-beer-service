package com.blazejknie.msscbeerservice.web.controller;

public class NotFoundException extends RuntimeException{

    public NotFoundException(String message) {
        super(message);
    }
}
