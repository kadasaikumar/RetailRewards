package com.chc.retail.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.chc.retail.dto.MonthlyRewards;
import com.chc.retail.dto.TotalRewardPoints;
import com.chc.retail.dto.TransactionData;
import com.chc.retail.entity.Customer;
import com.chc.retail.entity.RewardPoints;
import com.chc.retail.entity.Transaction;
import com.chc.retail.repository.CustomerRepository;
import com.chc.retail.repository.RewardPointsRepo;
import com.chc.retail.repository.TransactionRepository;
import com.chc.retail.util.CommonUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {TransactionServiceImpl.class, CommonUtil.class})
@ActiveProfiles({"DEV"})
@ExtendWith(SpringExtension.class)
class TransactionServiceImplTest {
    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private RewardPointsRepo rewardPointsRepo;

    @MockBean
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Test
    void createTransaction() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("jan*****@example.org", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase1() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("UUUU");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("UUU*", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase2() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("Loyalty Points calculated - {}");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("Loy***************************", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase3() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("(?<!^\\+)[0-9](?=[0-9]{4})");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("(?<**********************", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase4() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("(^[^@]{3}|(?!^)\\G)[^@]");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("(^[*@]{3}|(?!^)\\G)[^@]", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase5() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("Email");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("Ema**", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase6() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("com.chc.retail.entity.Customer");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        transactionData.setAmount(valueOfResult);
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("com***************************", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        BigDecimal expectedPoints = valueOfResult.ZERO;
        assertEquals(expectedPoints, transactionData.getPoints());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void createTransactionCase7() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);

        RewardPoints rewardPoints = new RewardPoints();
        rewardPoints.setSerialNum(0L);
        rewardPoints.setPoints(0);
        rewardPoints.setPurchaseAmount(BigDecimal.valueOf(42L));

        ArrayList<RewardPoints> rewardPointsList = new ArrayList<>();
        rewardPointsList.add(rewardPoints);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(rewardPointsList);

        Customer customer1 = new Customer();
        customer1.setLastName("Doe");
        customer1.setEmail("jane.doe@example.org");
        customer1.setCustId(123L);
        customer1.setMobileNumber("42");
        customer1.setFirstName("Jane");
        Optional<Customer> ofResult = Optional.of(customer1);
        when(this.customerRepository.findById((Long) any())).thenReturn(ofResult);

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        transactionData.setAmount(BigDecimal.valueOf(42L));
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        Transaction actualCreateTransactionResult = this.transactionServiceImpl.createTransaction(transactionData);
        assertSame(transaction, actualCreateTransactionResult);
        assertEquals(42L, actualCreateTransactionResult.getTotal().longValue());
        assertEquals("42", actualCreateTransactionResult.getPoints().toString());
        Customer customer2 = actualCreateTransactionResult.getCustomer();
        assertEquals("42", customer2.getMobileNumber());
        assertEquals("42", actualCreateTransactionResult.getAmount().toString());
        assertEquals("jan*****@example.org", customer2.getEmail());
        verify(this.transactionRepository).save((Transaction) any());
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
        assertEquals("TransactionData(particulars=Particulars, amount=42, status=1, refund=true, time=Thu Jan 01 05:30:00"
                + " IST 1970, location=Location, points=0, custId=123)", transactionData.toString());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionDetailsWhenMapping() throws DataFormatException {
        Customer customer = new Customer();
        customer.setLastName("Doe");
        customer.setEmail("jane.doe@example.org");
        customer.setCustId(123L);
        customer.setMobileNumber("42");
        customer.setFirstName("Jane");

        Transaction transaction = new Transaction();
        transaction.setRefund(true);
        transaction.setLocation("Location");
        transaction.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        transaction.setTime(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        transaction.setStatus(1);
        transaction.setParticulars("Particulars");
        transaction.setAmount(BigDecimal.valueOf(42L));
        transaction.setCustomer(customer);
        transaction.setId(123L);
        transaction.setTotal(1L);
        when(this.transactionRepository.save((Transaction) any())).thenReturn(transaction);
        when(this.rewardPointsRepo.findAllByOrderByPurchaseAmountDesc()).thenReturn(new ArrayList<>());
        when(this.customerRepository.findById((Long) any())).thenReturn(Optional.empty());

        TransactionData transactionData = new TransactionData();
        transactionData.setRefund(true);
        transactionData.setLocation("Location");
        transactionData.setPoints(BigDecimal.valueOf(42L));
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        transactionData.setTime(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        transactionData.setAmount(BigDecimal.valueOf(42L));
        transactionData.setStatus(1);
        transactionData.setParticulars("Particulars");
        transactionData.setCustId(123L);
        assertThrows(DataFormatException.class, () -> this.transactionServiceImpl.createTransaction(transactionData));
        verify(this.rewardPointsRepo).findAllByOrderByPurchaseAmountDesc();
        verify(this.customerRepository).findById((Long) any());
    }

    @Test
    void findAllTransactionsByTimeWhenRequestedByAdmin() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findAllByOrderByTime((org.springframework.data.domain.Pageable) any()))
                .thenReturn(transactionList);
        List<Transaction> actualFindAllTransactionsByTimeResult = this.transactionServiceImpl.findAllTransactionsByTime(1,
                3);
        assertSame(transactionList, actualFindAllTransactionsByTimeResult);
        assertTrue(actualFindAllTransactionsByTimeResult.isEmpty());
        verify(this.transactionRepository).findAllByOrderByTime((org.springframework.data.domain.Pageable) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenCustomerMobileFindTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findByCustomer_MobileNumber((String) any())).thenReturn(transactionList);
        List<Transaction> actualFindTransactionsOfCustomerByMobileResult = this.transactionServiceImpl
                .findTransactionsOfCustomerByMobile("Mobile");
        assertSame(transactionList, actualFindTransactionsOfCustomerByMobileResult);
        assertTrue(actualFindTransactionsOfCustomerByMobileResult.isEmpty());
        verify(this.transactionRepository).findByCustomer_MobileNumber((String) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenCustomerEmailFindTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findByCustomer_Email((String) any())).thenReturn(transactionList);
        List<Transaction> actualFindTransactionsOfCustomerByEmailResult = this.transactionServiceImpl
                .findTransactionsOfCustomerByEmail("jane.doe@example.org");
        assertSame(transactionList, actualFindTransactionsOfCustomerByEmailResult);
        assertTrue(actualFindTransactionsOfCustomerByEmailResult.isEmpty());
        verify(this.transactionRepository).findByCustomer_Email((String) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenCustomerIdFindTransactionsOfCustomer() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findByCustomer_CustId((Long) any())).thenReturn(transactionList);
        List<Transaction> actualFindTransactionsOfCustomerResult = this.transactionServiceImpl
                .findTransactionsOfCustomer(123L);
        assertSame(transactionList, actualFindTransactionsOfCustomerResult);
        assertTrue(actualFindTransactionsOfCustomerResult.isEmpty());
        verify(this.transactionRepository).findByCustomer_CustId((Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenCustomerFindAllRejectedTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findByStatusEqualsAndCustomer_CustId(anyInt(), (Long) any()))
                .thenReturn(transactionList);
        List<Transaction> actualFindAllRejectedResult = this.transactionServiceImpl.findAllRejected(123L);
        assertSame(transactionList, actualFindAllRejectedResult);
        assertTrue(actualFindAllRejectedResult.isEmpty());
        verify(this.transactionRepository).findByStatusEqualsAndCustomer_CustId(anyInt(), (Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenCustomerIdAllRefundedTransactions() {
        ArrayList<Transaction> transactionList = new ArrayList<>();
        when(this.transactionRepository.findByStatusEqualsAndCustomer_CustId(anyInt(), (Long) any()))
                .thenReturn(transactionList);
        List<Transaction> actualFindAllRefundedResult = this.transactionServiceImpl.findAllRefunded(123L);
        assertSame(transactionList, actualFindAllRefundedResult);
        assertTrue(actualFindAllRefundedResult.isEmpty());
        verify(this.transactionRepository).findByStatusEqualsAndCustomer_CustId(anyInt(), (Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenCustomer() {
        when(this.transactionRepository.sumOfAllPoints((Long) any())).thenReturn(1);
        assertEquals(1, this.transactionServiceImpl.totalRewardPerCustomer(123L));
        verify(this.transactionRepository).sumOfAllPoints((Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenCustomerByEmail() {
        when(this.transactionRepository.sumOfAllPointsByEmail((String) any())).thenReturn(1);
        assertEquals(1, this.transactionServiceImpl.totalRewardPerCustomerByEmail("jane.doe@example.org"));
        verify(this.transactionRepository).sumOfAllPointsByEmail((String) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenCustomerByMobile() {
        when(this.transactionRepository.sumOfAllPointsByMobile((String) any())).thenReturn(1);
        assertEquals(1, this.transactionServiceImpl.totalRewardPerCustomerByMobile("Mobile"));
        verify(this.transactionRepository).sumOfAllPointsByMobile((String) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenCalculatingTotalRewards() {
        when(this.transactionRepository.rewardPointsForCustomer((String) any(), (java.util.Date) any()))
                .thenReturn(new ArrayList<>());
        this.transactionServiceImpl.totalRewardPoints("42", 1L);
        verify(this.transactionRepository).rewardPointsForCustomer((String) any(), (java.util.Date) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenCalculatingTotalRewardsMatch() {
        ArrayList<Object[]> objectArrayList = new ArrayList<>();
        BigDecimal valueOfResult = BigDecimal.valueOf(42L);
        objectArrayList.add(new Object[]{"42", "42", valueOfResult});
        when(this.transactionRepository.rewardPointsForCustomer((String) any(), (java.util.Date) any()))
                .thenReturn(objectArrayList);
        TotalRewardPoints actualTotalRewardPointsResult = this.transactionServiceImpl.totalRewardPoints("42", 1L);
        List<MonthlyRewards> monthlyRewardPoints = actualTotalRewardPointsResult.getMonthlyRewardPoints();
        assertEquals(1, monthlyRewardPoints.size());
        BigDecimal totalRewardPoints = actualTotalRewardPointsResult.getTotalRewardPoints();
        assertEquals(valueOfResult, totalRewardPoints);
        assertEquals("42", totalRewardPoints.toString());
        MonthlyRewards getResult = monthlyRewardPoints.get(0);
        BigDecimal rewardPoints = getResult.getRewardPoints();
        assertEquals(totalRewardPoints, rewardPoints);
        assertEquals("42", getResult.getMonth());
        assertEquals("42", getResult.getYear());
        assertEquals("42", rewardPoints.toString());
        verify(this.transactionRepository).rewardPointsForCustomer((String) any(), (java.util.Date) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenRefunded() {
        when(this.transactionRepository.updateTxnRefund((Long) any())).thenReturn(1);
        assertEquals(1, this.transactionServiceImpl.refundTxn(123L));
        verify(this.transactionRepository).updateTxnRefund((Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }

    @Test
    void givenTransactionWhenRejected() {
        when(this.transactionRepository.updateTxnReject((Long) any())).thenReturn(1);
        assertEquals(1, this.transactionServiceImpl.rejectTxn(123L));
        verify(this.transactionRepository).updateTxnReject((Long) any());
        assertTrue(this.transactionServiceImpl.findAllTransactions().isEmpty());
    }
}

