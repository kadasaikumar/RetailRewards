package com.chc.retail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chc.retail.dto.CustomerData;
import com.chc.retail.entity.Customer;
import com.chc.retail.exception.CustomerNotFoundException;
import com.chc.retail.exception.NoDataFoundException;
import com.chc.retail.repository.CustomerRepository;
import com.chc.retail.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerServiceImpl.class, CommonUtil.class})
@ActiveProfiles({"DEV"})
@ExtendWith(SpringExtension.class)
class CustomerServiceImplTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerServiceImpl customerServiceImpl;

    @Test
    void createNewCustomer() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerData customerData = new CustomerData();
        customerData.setLastName("Doe");
        customerData.setEmail("jane.doe@example.org");
        customerData.setMobile("Mobile");
        customerData.setFirstName("Jane");
        Customer actualSaveCustomerResult = this.customerServiceImpl.saveCustomer(customerData);
        assertSame(customer, actualSaveCustomerResult);
        assertEquals("42", actualSaveCustomerResult.getMobileNumber());
        assertEquals("jan*****@example.org", actualSaveCustomerResult.getEmail());
        verify(this.customerRepository).save((Customer) any());
    }

    @Test
    void givenCustomerDetailsWhenSaved() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("UUUU");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerData customerData = new CustomerData();
        customerData.setLastName("Doe");
        customerData.setEmail("jane.doe@example.org");
        customerData.setMobile("Mobile");
        customerData.setFirstName("Jane");
        Customer actualSaveCustomerResult = this.customerServiceImpl.saveCustomer(customerData);
        assertSame(customer, actualSaveCustomerResult);
        assertEquals("42", actualSaveCustomerResult.getMobileNumber());
        assertEquals("UUU*", actualSaveCustomerResult.getEmail());
        verify(this.customerRepository).save((Customer) any());
    }

    @Test
    void givenCustomerWhenDataMasked() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("(^[^@]{3}|(?!^)\\G)[^@]");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerData customerData = new CustomerData();
        customerData.setLastName("Doe");
        customerData.setEmail("jane.doe@example.org");
        customerData.setMobile("Mobile");
        customerData.setFirstName("Jane");
        Customer actualSaveCustomerResult = this.customerServiceImpl.saveCustomer(customerData);
        assertSame(customer, actualSaveCustomerResult);
        assertEquals("42", actualSaveCustomerResult.getMobileNumber());
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]", actualSaveCustomerResult.getEmail());
        verify(this.customerRepository).save((Customer) any());
    }

    @Test
    void givenCustomerDetailsWhenEmailPattrenMatches() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("(?<!^\\+)[0-9](?=[0-9]{4})");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        when(this.customerRepository.save((Customer) any())).thenReturn(customer);

        CustomerData customerData = new CustomerData();
        customerData.setLastName("Doe");
        customerData.setEmail("jane.doe@example.org");
        customerData.setMobile("Mobile");
        customerData.setFirstName("Jane");
        Customer actualSaveCustomerResult = this.customerServiceImpl.saveCustomer(customerData);
        assertSame(customer, actualSaveCustomerResult);
        assertEquals("42", actualSaveCustomerResult.getMobileNumber());
        assertEquals("(?<**********************", actualSaveCustomerResult.getEmail());
        verify(this.customerRepository).save((Customer) any());
    }

    @Test
    void givenCustomerByMobile() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer);
        when(this.customerRepository.findByMobileNumber((String) any())).thenReturn(ofResult);
        assertTrue(this.customerServiceImpl.customerByMobile("Mobile").isPresent());
        verify(this.customerRepository).findByMobileNumber((String) any());
    }

    @Test
    void givenCustomerByMobileWhenCustomerNotFound() {
        when(this.customerRepository.findByMobileNumber((String) any())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class, () -> this.customerServiceImpl.customerByMobile("Mobile"));
        verify(this.customerRepository).findByMobileNumber((String) any());
    }

    @Test
    void givenCustomerByEmail() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer);
        when(this.customerRepository.findByEmail((String) any())).thenReturn(ofResult);
        assertTrue(this.customerServiceImpl.customerByEmail("jane.doe@example.org").isPresent());
        verify(this.customerRepository).findByEmail((String) any());
    }

    @Test
    void givenCustomerByEmailWhenCustomerNotFound() {
        when(this.customerRepository.findByEmail((String) any())).thenReturn(Optional.empty());
        assertThrows(CustomerNotFoundException.class,
                () -> this.customerServiceImpl.customerByEmail("jane.doe@example.org"));
        verify(this.customerRepository).findByEmail((String) any());
    }

    @Test
    void allCustomersWhenNoConstraints() {
        when(this.customerRepository.findAll((org.springframework.data.domain.Sort) any())).thenReturn(new ArrayList<>());
        assertThrows(NoDataFoundException.class, () -> this.customerServiceImpl.allCustomers());
        verify(this.customerRepository).findAll((org.springframework.data.domain.Sort) any());
    }

    @Test
    void allCustomersWhenSizeCheck() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        ArrayList<Customer> customerList = new ArrayList<>();
        customerList.add(customer);
        when(this.customerRepository.findAll((org.springframework.data.domain.Sort) any())).thenReturn(customerList);
        List<Customer> actualAllCustomersResult = this.customerServiceImpl.allCustomers();
        assertSame(customerList, actualAllCustomersResult);
        assertEquals(1, actualAllCustomersResult.size());
        verify(this.customerRepository).findAll((org.springframework.data.domain.Sort) any());
    }

    @Test
    void givenCustomerToDelete() {
        doNothing().when(this.customerRepository).deleteById((Long) any());
        this.customerServiceImpl.deleteCustomer(123L);
        verify(this.customerRepository).deleteById((Long) any());
    }

    @Test
    void givenCustomerWhenDataMaskedForEmail() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        assertNull(this.customerServiceImpl.maskDataFields(customer));
        assertEquals("42", customer.getMobileNumber());
        assertEquals("jan*****@example.org", customer.getEmail());
    }


    @Test
    void givenMaskedDataWithRegExCase() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("com.chc.retail.entity.Customer");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        assertNull(this.customerServiceImpl.maskDataFields(customer));
        assertEquals("42", customer.getMobileNumber());
        assertEquals("com***************************", customer.getEmail());
    }

    @Test
    void givenMaskedDataWhenMatchingWithMultipleDot() {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.orgjane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");
        assertNull(this.customerServiceImpl.maskDataFields(customer));
        assertEquals("42", customer.getMobileNumber());
        assertEquals("jan*****@example.orgjane.doe@example.org", customer.getEmail());
    }
}

