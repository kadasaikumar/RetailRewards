package com.chc.retail.api;

import com.chc.retail.dto.MonthlyRewards;
import com.chc.retail.dto.TotalRewardPoints;
import com.chc.retail.dto.TransactionData;
import com.chc.retail.entity.Customer;
import com.chc.retail.entity.Transaction;
import com.chc.retail.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TransactionController.class})
@ActiveProfiles({"DEV"})
@ExtendWith(SpringExtension.class)
class TransactionControllerTest {

  @MockBean private TransactionService transactionService;

  @Autowired private TransactionController transactionController;

  private ModelMapper modelMapper = new ModelMapper();

  @SneakyThrows
  @Test
  void createNewTransactionWhenDetailsProvided() {

    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(101.72));
    txnData.setRefund(false);
    txnData.setStatus(1);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);

    when(transactionService.createTransaction((TransactionData) any())).thenReturn(transaction);
    transactionService.createTransaction(txnData);

    String content = (new ObjectMapper()).writeValueAsString(txnData);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/transaction")
            .contentType(MediaType.APPLICATION_JSON)
            .content(content);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenPageSizeThenHistoryCustomerTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(101.72));
    txnData.setRefund(false);
    txnData.setStatus(1);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findAllTransactionsByTime(anyInt(), anyInt())).thenReturn(transactions);
    transactionService.findAllTransactionsByTime(1, 1);

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/{page}/{size}", 1, 1)
            .contentType(MediaType.APPLICATION_JSON);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenCustomerIdThenCustomerTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(101.72));
    txnData.setRefund(false);
    txnData.setStatus(1);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findTransactionsOfCustomer(anyLong())).thenReturn(transactions);
    transactionService.findTransactionsOfCustomer(100L);

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/{custId}", 100)
            .contentType(MediaType.APPLICATION_JSON);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenCustomerMobileThenCustomerTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(101.72));
    txnData.setRefund(false);
    txnData.setStatus(1);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findTransactionsOfCustomerByMobile(anyString()))
        .thenReturn(transactions);
    transactionService.findTransactionsOfCustomerByMobile("9999988888");

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/mobile/{mobileNumber}", "9999988888")
            .contentType(MediaType.APPLICATION_JSON);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenCustomerEmailThenCustomerTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(101.72));
    txnData.setRefund(false);
    txnData.setStatus(1);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findTransactionsOfCustomerByEmail(anyString()))
        .thenReturn(transactions);
    transactionService.findTransactionsOfCustomerByEmail("johncena@wwe.com");

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/email/{email}", "johncena@wwe.com")
            .contentType(MediaType.APPLICATION_JSON);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenCustomerIdWhenTransactionRejectedThenListOfRejectedTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(0));
    txnData.setRefund(false);
    txnData.setStatus(3);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findAllRejected(anyLong())).thenReturn(transactions);
    transactionService.findAllRejected(100L);

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/rejected/{custId}", 100L)
            .contentType(MediaType.APPLICATION_JSON);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenCustomerIdWhenTransactionRefundedThenListOfRefundedTransactions() {
    List<Transaction> transactions = new ArrayList<>();
    TransactionData txnData = new TransactionData();
    Customer customer = new Customer();

    customer.setFirstName("john");
    customer.setLastName("cena");
    customer.setCustId(100L);
    customer.setEmail("johncena@wwe.com");
    customer.setMobileNumber("9999988888");

    txnData.setAmount(BigDecimal.valueOf(125.86));
    txnData.setCustId(100);
    txnData.setLocation("Xinjie");
    txnData.setParticulars("Choco Lava");
    txnData.setPoints(BigDecimal.valueOf(0));
    txnData.setRefund(true);
    txnData.setStatus(4);
    txnData.setTime(timeStamp());

    final Transaction transaction = modelMapper.map(txnData, Transaction.class);
    transaction.setCustomer(customer);
    transactions.add(transaction);
    when(transactionService.findAllRefunded(anyLong())).thenReturn(transactions);
    transactionService.findAllRefunded(100L);

    String content = (new ObjectMapper()).writeValueAsString(transactions);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post("/api/purchase/history/refund/{custId}", 100L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  @Test
  void givenTransactionIdWhenTransactionRejected() {

    when(transactionService.rejectTxn(anyLong())).thenReturn(1);
    transactionService.rejectTxn(1000L);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/api/purchase/reject/{txnId}", 1000L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(MockMvcResultMatchers.content().string("Transaction Rejected successfully !"));
  }

  @SneakyThrows
  @Test
  void givenTransactionIdWhenTransactionRefunded() {
    when(transactionService.refundTxn(anyLong())).thenReturn(1);
    transactionService.refundTxn(1000L);

    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.put("/api/purchase/refund/{txnId}", 1000L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
        .andExpect(MockMvcResultMatchers.content().string("Refunded successfully !"));
  }
  /// rewards/{mobileNumber}/{numberOfMonths}

  @SneakyThrows
  @Test
  void findTotalRewardPoints() {
    TotalRewardPoints totalRewardPoints = new TotalRewardPoints();
    MonthlyRewards janMonthRewards = new MonthlyRewards();
    List<MonthlyRewards> monthlyRewardPoints = new ArrayList<>();
    janMonthRewards.setMonth("January");
    janMonthRewards.setYear("2023");
    janMonthRewards.setRewardPoints(BigDecimal.valueOf(12.86));
    MonthlyRewards decMonthRewards = new MonthlyRewards();
    decMonthRewards.setMonth("December");
    decMonthRewards.setYear("2022");
    decMonthRewards.setRewardPoints(BigDecimal.valueOf(20.00));
    MonthlyRewards novMonthRewards = new MonthlyRewards();
    novMonthRewards.setMonth("November");
    novMonthRewards.setYear("2022");
    novMonthRewards.setRewardPoints(BigDecimal.valueOf(35.00));
    MonthlyRewards octMonthRewards = new MonthlyRewards();
    octMonthRewards.setMonth("October");
    octMonthRewards.setYear("2022");
    octMonthRewards.setRewardPoints(BigDecimal.valueOf(26.00));
    MonthlyRewards sepMonthRewards = new MonthlyRewards();
    sepMonthRewards.setMonth("September");
    sepMonthRewards.setYear("2022");
    sepMonthRewards.setRewardPoints(BigDecimal.valueOf(30.00));
    MonthlyRewards augMonthRewards = new MonthlyRewards();
    augMonthRewards.setMonth("August");
    augMonthRewards.setYear("2022");
    augMonthRewards.setRewardPoints(BigDecimal.valueOf(10.00));
    MonthlyRewards julyMonthRewards = new MonthlyRewards();
    julyMonthRewards.setMonth("July");
    julyMonthRewards.setYear("2022");
    julyMonthRewards.setRewardPoints(BigDecimal.valueOf(08.00));
    monthlyRewardPoints.add(julyMonthRewards);
    monthlyRewardPoints.add(augMonthRewards);
    monthlyRewardPoints.add(sepMonthRewards);
    monthlyRewardPoints.add(octMonthRewards);
    monthlyRewardPoints.add(novMonthRewards);
    monthlyRewardPoints.add(decMonthRewards);
    monthlyRewardPoints.add(janMonthRewards);
    totalRewardPoints.setTotalRewardPoints(BigDecimal.valueOf(141.86));
    totalRewardPoints.setMonthlyRewardPoints(monthlyRewardPoints);
    when(transactionService.totalRewardPoints(anyString(), anyLong()))
        .thenReturn(totalRewardPoints);
    transactionService.totalRewardPoints("9999988888", 12L);
    String content = (new ObjectMapper()).writeValueAsString(totalRewardPoints);
    MockHttpServletRequestBuilder requestBuilder =
        MockMvcRequestBuilders.post(
            "/api/purchase/rewards/{mobileNumber}/{numberOfMonths}", "9999988888", 12L);
    ResultActions actualPerformResult =
        MockMvcBuilders.standaloneSetup(transactionController).build().perform(requestBuilder);
    actualPerformResult
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
        .andExpect(MockMvcResultMatchers.content().string(content));
  }

  @SneakyThrows
  private Date timeStamp() {
    String string = "January 20, 2023";
    DateFormat format = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
    return format.parse(string);
  }
}
