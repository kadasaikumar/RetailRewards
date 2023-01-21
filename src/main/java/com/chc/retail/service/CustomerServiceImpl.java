package com.chc.retail.service;

import com.chc.retail.dto.CustomerData;
import com.chc.retail.entity.Customer;
import com.chc.retail.exception.CustomerNotFoundException;
import com.chc.retail.exception.NoDataFoundException;
import com.chc.retail.repository.CustomerRepository;
import com.chc.retail.util.CommonUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

  private CustomerRepository customerRepo;
  private CommonUtil commonUtil;

  public CustomerServiceImpl(CustomerRepository customerRepo, CommonUtil commonUtil) {
    this.customerRepo = customerRepo;
    this.commonUtil = commonUtil;
  }

  @Override
  public Customer saveCustomer(CustomerData customerDto) {
    Customer savedObject = customerRepo.save(customerDtoToEntity(customerDto));
    savedObject.setEmail(commonUtil.maskEmail(savedObject.getEmail()));
    savedObject.setMobileNumber(commonUtil.maskMobile(savedObject.getMobileNumber()));
    return savedObject;
  }

  @Override
  public Optional<Customer> customerByMobile(String mobile) {
    return Optional.ofNullable(
        customerRepo
            .findByMobileNumber(mobile)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException(
                        "Customer Not Found With the Mobile Provided !")));
  }

  @Override
  public Optional<Customer> customerByEmail(String email) {
    return Optional.ofNullable(
        customerRepo
            .findByEmail(email)
            .orElseThrow(
                () ->
                    new CustomerNotFoundException("Customer Not Found With the Email Provided !")));
  }

  @Override
  public List<Customer> allCustomers() {
    final List<Customer> customers = customerRepo.findAll(Sort.by(Sort.Direction.DESC, "custId"));
    if (customers.isEmpty()) {
      throw new NoDataFoundException();
    }
    return customers;
  }

  @Override
  public void deleteCustomer(long custId) {
    customerRepo.deleteById(custId);
  }

  private Customer customerDtoToEntity(CustomerData customerDto) {
    Customer customer = new Customer();
    customer.setEmail(customerDto.getEmail());
    customer.setMobileNumber(customerDto.getMobile());
    customer.setFirstName(customerDto.getFirstName());
    customer.setLastName(customerDto.getLastName());
    return customer;
  }
}
