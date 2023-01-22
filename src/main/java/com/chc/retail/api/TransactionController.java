package com.chc.retail.api;

import com.chc.retail.dto.TotalRewardPoints;
import com.chc.retail.dto.TransactionData;
import com.chc.retail.entity.Transaction;
import com.chc.retail.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.zip.DataFormatException;

import static com.chc.retail.config.Constants.*;

@RestController
@RequestMapping(API_PURCHASE)
public class TransactionController {

  private TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  @PostMapping(TRANSACTION)
  private ResponseEntity<Object> newTransaction(@Valid @RequestBody TransactionData txnData)
      throws DataFormatException {
    final Transaction transaction = transactionService.createTransaction(txnData);
    return new ResponseEntity<>(transaction, HttpStatus.CREATED);
  }

  @PostMapping(HISTORY_PAGE_SIZE)
  @PreAuthorize("hasAnyRole('ADMIN')")
  private ResponseEntity<Object> customerAllTransaction(
      @PathVariable int page, @PathVariable int size) {
    final List<Transaction> allTransactionsByTime =
        transactionService.findAllTransactionsByTime(page, size);
    return new ResponseEntity<>(allTransactionsByTime, HttpStatus.OK);
  }

  @PostMapping(HISTORY_CUST_ID)
  @PreAuthorize("hasAnyRole('DEV')")
  private ResponseEntity<Object> customerTransactionsById(@PathVariable Long custId) {
    final List<Transaction> transactionsOfCustomer =
        transactionService.findTransactionsOfCustomer(custId);
    return new ResponseEntity<>(transactionsOfCustomer, HttpStatus.OK);
  }

  @PostMapping(HISTORY_MOBILE_NUMBER)
  private ResponseEntity<Object> CustomerTransactionsByMobile(@PathVariable String mobileNumber) {
    final List<Transaction> transactionsOfCustomerByMobile =
        transactionService.findTransactionsOfCustomerByMobile(mobileNumber);
    return new ResponseEntity<>(transactionsOfCustomerByMobile, HttpStatus.OK);
  }

  @PostMapping(HISTORY_EMAIL)
  private ResponseEntity<Object> CustomerTransactionsByEmail(@PathVariable String email) {
    final List<Transaction> transactionsOfCustomerByEmail =
        transactionService.findTransactionsOfCustomerByEmail(email);
    return new ResponseEntity<>(transactionsOfCustomerByEmail, HttpStatus.OK);
  }

  @PostMapping(HISTORY_REJECTED_CUST_ID)
  private ResponseEntity<Object> CustomerTransactionsRejected(@PathVariable Long custId) {
    final List<Transaction> transactionsOfCustomerByEmail =
        transactionService.findAllRejected(custId);
    return new ResponseEntity<>(transactionsOfCustomerByEmail, HttpStatus.OK);
  }

  @PostMapping(HISTORY_REFUNDED_CUST_ID)
  private ResponseEntity<Object> CustomerTransactionsRefund(@PathVariable Long custId) {
    final List<Transaction> transactionsOfCustomerByEmail =
        transactionService.findAllRefunded(custId);
    return new ResponseEntity<>(transactionsOfCustomerByEmail, HttpStatus.OK);
  }

  @PutMapping(REFUND_TXN_ID)
  private ResponseEntity<Object> CustomerTransactionRefund(@PathVariable Long txnId) {
    final int updateStatus = transactionService.refundTxn(txnId);
    String refundMsg = updateStatus > 0 ? "Refunded successfully !" : " Refund Failed";
    return new ResponseEntity<>(refundMsg, HttpStatus.OK);
  }

  @PutMapping(REJECT_TXN_ID)
  private ResponseEntity<Object> CustomerTransactionReject(@PathVariable Long txnId) {
    final int rejectStatus = transactionService.rejectTxn(txnId);
    String rejectMsg =
        rejectStatus > 0 ? "Transaction Rejected successfully !" : " Transaction Rejection Failed";
    return new ResponseEntity<>(rejectMsg, HttpStatus.OK);
  }

  @PostMapping(REWARDS_MOBILE_NUMBER_Months)
  private ResponseEntity<Object> customerRewardsById(
      @PathVariable String mobileNumber, @PathVariable Long numberOfMonths) {
    final TotalRewardPoints totalRewardPoints =
        transactionService.totalRewardPoints(mobileNumber, numberOfMonths);
    return new ResponseEntity<>(totalRewardPoints, HttpStatus.OK);
  }
}
