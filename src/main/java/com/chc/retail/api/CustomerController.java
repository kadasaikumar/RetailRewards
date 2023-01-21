package com.chc.retail.api;

import com.chc.retail.config.Constants;
import com.chc.retail.dto.CustomerData;
import com.chc.retail.entity.Customer;
import com.chc.retail.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.chc.retail.config.Constants.*;

@RestController
@RequestMapping(API)
@Slf4j
public class CustomerController {

  private CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping(CUSTOMER)
  private ResponseEntity<Object> createNewCustomer(@Valid @RequestBody CustomerData customer) {
    Customer savedCustomer = customerService.saveCustomer(customer);
    return new ResponseEntity<>(savedCustomer, HttpStatus.CREATED);
  }

  @PostMapping(CUSTOMERS)
  // @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<List<Customer>> allCustomers() {
    final List<Customer> customers = customerService.allCustomers();
    return new ResponseEntity<>(customers, HttpStatus.OK);
  }

  @PostMapping(CUSTOMER_MOBILE_MOBILE_NUMBER)
  public ResponseEntity<Object> customerByMobile(@PathVariable String mobileNumber) {
    final Optional<Customer> customer = customerService.customerByMobile(mobileNumber);
    if (customer.isPresent()) {
      return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Data Not Found!", HttpStatus.OK);
    }
  }

  @PostMapping(CUSTOMER_EMAIL_EMAIL)
  public ResponseEntity<Object> customerByEmail(@PathVariable String email) {
    final Optional<Customer> customer = customerService.customerByEmail(email);
    if (customer.isPresent()) {
      return new ResponseEntity<>(customer.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>("Data Not Found!", HttpStatus.OK);
    }
  }

  @DeleteMapping(CUSTOMER_CUST_ID)
  public ResponseEntity<String> deleteCustomer(@PathVariable String custId) {
    customerService.deleteCustomer(Long.valueOf(custId));
    return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
  }
}
