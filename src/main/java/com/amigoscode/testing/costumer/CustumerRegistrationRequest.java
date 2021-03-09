package com.amigoscode.testing.costumer;

import com.fasterxml.jackson.annotation.JsonProperty;

//Class that represent a request from a client
public class CustumerRegistrationRequest {
    private final Custumer custumer;

    public CustumerRegistrationRequest(
            @JsonProperty("customer") Custumer custumer){
        this.custumer=custumer;
    }

    public Custumer getCustumer() {
        return custumer;
    }

    @Override
    public String toString() {
        return "CustumerRegistrationRequest{" +
                "custumer=" + custumer +
                '}';
    }
}
