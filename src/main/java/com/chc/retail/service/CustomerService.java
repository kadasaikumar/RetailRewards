package com.chc.retail.service;

import com.chc.retail.dto.CustomerData;
import com.chc.retail.entity.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerService {

  Customer saveCustomer(CustomerData customerDto);

  Optional<Customer> customerByMobile(String mobile);

  Optional<Customer> customerByEmail(String email);

  List<Customer> allCustomers();

  void deleteCustomer(Long custId);

  Customer maskDataFields(Customer customer);
}
