package com.amigoscode.testing.costumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@DataJpaTest(properties = {
        "spring.jpa.properties.javax.persistence.validation.mode=none"
})
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;

    @Test
    void itShouldSelectCustomerByPhoneNUmber() {
        //Given
        //When
        //Then

    }

    @Test
    void itShouldSaveCustomer() {
        //Given

        UUID id=UUID.randomUUID();
        Custumer customer= new Custumer(id,"Abel","0000");
        //When
        underTest.save(customer);
        //Then
        Optional<Custumer> optionalCustumer=underTest.findById(id);
        assertThat(optionalCustumer).isPresent().hasValueSatisfying(c ->{
            assertThat(c.getId()).isEqualTo(id);
            assertThat(c.getName()).isEqualTo(customer.getName());
            assertThat(c.getPhoneNumber()).isEqualTo("0000");


            assertThat(c).isEqualToComparingFieldByField(customer);
                });

    }

    @Test
    void itShouldNotSaveCustomerWhenNameIsNull() {
        //Given
        UUID id=UUID.randomUUID();
        Custumer customer= new Custumer(id,null,"0000");
        //When

        //Then

        assertThrows(DataIntegrityViolationException.class,()->underTest.save(customer));
       // assertTrue(customer.getName()!=null);


    }
}
