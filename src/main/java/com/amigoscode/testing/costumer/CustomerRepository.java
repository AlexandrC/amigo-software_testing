package com.amigoscode.testing.costumer;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository extends CrudRepository<Custumer, UUID> {
    @Query(value = "select id, name, phone_number from customer where phone_number = :phone_number",
    nativeQuery = true)
    Optional<Custumer> selectCustomerByPhoneNumber(@Param("phone_number") String phoneNumber);
}
