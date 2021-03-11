package com.amigoscode.testing.costumer.exceptions;

public class CustomerWithSamePhoneNumberException extends RuntimeException {
    public CustomerWithSamePhoneNumberException(String errorMessage){
        super(errorMessage);
    }
}
