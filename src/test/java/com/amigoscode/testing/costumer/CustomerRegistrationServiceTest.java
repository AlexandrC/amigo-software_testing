package com.amigoscode.testing.costumer;

import com.amigoscode.testing.costumer.exceptions.CustomerWithSamePhoneNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

class CustomerRegistrationServiceTest {
    @Mock
    private CustomerRepository customerRepository;

    @Captor
    private ArgumentCaptor<Custumer> custumerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        underTest = new CustomerRegistrationService(customerRepository);
    }

    @Test
    void itShouldSaveNewCustomer() {
        //Given
        String phoneNumber = "0000";
        Custumer customer = new Custumer(UUID.randomUUID(), "Maxim", phoneNumber);
        // ... a request
        CustumerRegistrationRequest request = new CustumerRegistrationRequest(customer);

        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.empty());
        //When

        underTest.registerNewCustomer(request);
        //Then
        then(customerRepository).should().save(custumerArgumentCaptor.capture());
        Custumer custumerArgumentCaptorValue = custumerArgumentCaptor.getValue();
        assertEquals(custumerArgumentCaptorValue, customer);
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExist() {
        //Given
        String phoneNumber = "0000";
        Custumer customer = new Custumer(UUID.randomUUID(), "Maxim", phoneNumber);
        // ... a request
        CustumerRegistrationRequest request = new CustumerRegistrationRequest(customer);

        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));

        //When
        underTest.registerNewCustomer(request);
        //Then
        then(customerRepository).should(never()).save(any());
        then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldThrowWhenPhoneNumberIstaken() {

        //Given
        String phoneNumber = "0000";
        Custumer customer = new Custumer(UUID.randomUUID(), "Maxim", phoneNumber);
        Custumer custumertwo = new Custumer(UUID.randomUUID(), "John", phoneNumber);
        // ... a request
        CustumerRegistrationRequest request = new CustumerRegistrationRequest(customer);


        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(custumertwo));

        //When
        CustomerWithSamePhoneNumberException exception=assertThrows(CustomerWithSamePhoneNumberException.class, () -> underTest.registerNewCustomer(request));
        //Then
        assertEquals(exception.getMessage(),(String.format("phone number [%s] is taken",phoneNumber)));

        //Finally
        then(customerRepository).should(never()).save(any(Custumer.class));
    }

    @Test
    void itShouldReturnVoidIfCustomerAlreadyExist() {

        //Given
        String phoneNumber = "0000";
        Custumer customer = new Custumer(UUID.randomUUID(), "Maxim", phoneNumber);
        CustumerRegistrationRequest request = new CustumerRegistrationRequest(customer);
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));
        //When
        underTest.registerNewCustomer(request);

        //Then
        assertEquals(request.getCustumer().getName(),customer.getName());
      //  then(customerRepository).should(never()).save(any(Custumer.class));
      //  then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
      //  then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void itShouldGenerateId() {
        //Given
        String phoneNumber = "0000";
        Custumer customer = new Custumer(null, "Maxim", phoneNumber);
        CustumerRegistrationRequest request = new CustumerRegistrationRequest(customer);
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber))
                .willReturn(Optional.of(customer));
        //When
        underTest.registerNewCustomer(request);
        //Then
        assertTrue(request.getCustumer().getId()!=null);
    }

}
