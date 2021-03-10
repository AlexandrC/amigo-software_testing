package com.amigoscode.testing.costumer.exceptions;

public class CustomerWithSamePhoneNumber extends RuntimeException {
    public CustomerWithSamePhoneNumber(String errorMessage){
        super(errorMessage);
    }
}
