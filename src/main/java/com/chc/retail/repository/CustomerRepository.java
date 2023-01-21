package com.chc.retail.repository;

import com.chc.retail.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

  Optional<Customer> findByEmail(String email);

  Optional<Customer> findByMobileNumber(String mobile);
}
