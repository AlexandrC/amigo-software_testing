package com.amigoscode.testing.costumer;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/costumer-registration")
public class CustumerRegistrationControlller {

    @PutMapping
    public void registerNewCostumer(@Valid @RequestBody CustumerRegistrationRequest request){

    }
}
