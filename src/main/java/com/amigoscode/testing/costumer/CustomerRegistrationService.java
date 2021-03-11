package com.amigoscode.testing.costumer;

import com.amigoscode.testing.costumer.exceptions.CustomerWithSamePhoneNumberException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerRegistrationService {

    private final CustomerRepository customerRepository;


    @Autowired
    public CustomerRegistrationService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void registerNewCustomer(CustumerRegistrationRequest request){


        String phoneNumber=request.getCustumer().getPhoneNumber();
        if (request.getCustumer().getId()==null){
            request.getCustumer().setId(UUID.randomUUID());
        }
        Optional <Custumer> optionalCustumer=customerRepository.selectCustomerByPhoneNumber(phoneNumber);


        if (optionalCustumer.isPresent()){

            Custumer custumer= optionalCustumer.get();
            if (custumer.getName().equals(request.getCustumer().getName())) {
                return;
            }
            else {
                throw new CustomerWithSamePhoneNumberException(String.format("phone number [%s] is taken", phoneNumber));
            }

        }


        customerRepository.save(request.getCustumer());
        return;
    }
}
