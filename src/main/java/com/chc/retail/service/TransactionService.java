package com.chc.retail.service;

import com.chc.retail.dto.TotalRewardPoints;
import com.chc.retail.dto.TransactionData;
import com.chc.retail.entity.Transaction;

import java.util.List;
import java.util.zip.DataFormatException;

public interface TransactionService {

  String Greeting();

  Transaction createTransaction(TransactionData transactionData) throws DataFormatException;

  List<Transaction> findAllTransactions();

  List<Transaction> findAllTransactionsByTime(int page, int size);

  List<Transaction> findTransactionsOfCustomerByMobile(String mobile);

  List<Transaction> findTransactionsOfCustomerByEmail(String email);

  List<Transaction> findTransactionsOfCustomer(Long custId);

  List<Transaction> findAllRejected(Long custId);

  List<Transaction> findAllRefunded(Long custId);

  int totalRewardPerCustomer(Long custId);

  int totalRewardPerCustomerByEmail(String email);

  int totalRewardPerCustomerByMobile(String mobile);

  TotalRewardPoints totalRewardPoints(String mobileNumber, Long numberOfMonths);

  int refundTxn(Long txnId);

  int rejectTxn(Long txnId);
}
