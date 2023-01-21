package com.chc.retail.service;

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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

  public static final int REJECTED_STATUS = 3;
  public static final int REFUNDED_STATUS = 4;
  private TransactionRepository transactionRepository;
  private CustomerRepository customerRepo;
  private RewardPointsRepo pointsRepo;
  private CommonUtil commonUtil;

  public TransactionServiceImpl(
      TransactionRepository transactionRepository,
      CustomerRepository customerRepo,
      RewardPointsRepo pointsRepo,
      CommonUtil commonUtil) {
    this.transactionRepository = transactionRepository;
    this.customerRepo = customerRepo;
    this.pointsRepo = pointsRepo;
    this.commonUtil = commonUtil;
  }

  @Override
  public String Greeting() {
    return "It Works!!!!";
  }

  @Override
  public Transaction createTransaction(TransactionData transactionData) throws DataFormatException {
    transactionData.setPoints(calculatePoints(transactionData.getAmount()));
    // CommonUtil commonUtil = new CommonUtil();
    log.debug("Loyalty Points calculated - {}", transactionData);
    final Transaction savedTxn = transactionRepository.save(convertDtoToEntity(transactionData));
    savedTxn
        .getCustomer()
        .setMobileNumber(commonUtil.maskMobile(savedTxn.getCustomer().getMobileNumber()));
    savedTxn.getCustomer().setEmail(commonUtil.maskEmail(savedTxn.getCustomer().getEmail()));
    savedTxn.setTotal(savedTxn.getPoints().longValueExact());
    return savedTxn;
  }

  @Override
  public List<Transaction> findAllTransactions() {
    return (List<Transaction>) transactionRepository.findAll(Sort.by(Sort.Direction.DESC, "time"));
  }

  @Override
  public List<Transaction> findAllTransactionsByTime(int page, int size) {
    Pageable pageSize = PageRequest.of(page, size);
    return transactionRepository.findAllByOrderByTime(pageSize);
  }

  @Override
  public List<Transaction> findTransactionsOfCustomerByMobile(String mobile) {
    return transactionRepository.findByCustomer_MobileNumber(mobile);
  }

  @Override
  public List<Transaction> findTransactionsOfCustomerByEmail(String email) {
    return transactionRepository.findByCustomer_Email(email);
  }

  @Override
  public List<Transaction> findTransactionsOfCustomer(Long custId) {
    return transactionRepository.findByCustomer_CustId(custId);
  }

  @Override
  public List<Transaction> findAllRejected(Long custId) {
    return transactionRepository.findByStatusEqualsAndCustomer_CustId(REJECTED_STATUS, custId);
  }

  @Override
  public List<Transaction> findAllRefunded(Long custId) {
    return transactionRepository.findByStatusEqualsAndCustomer_CustId(REFUNDED_STATUS, custId);
  }

  @Override
  public int totalRewardPerCustomer(Long custId) {
    return transactionRepository.sumOfAllPoints(custId);
  }

  @Override
  public int totalRewardPerCustomerByEmail(String email) {
    return transactionRepository.sumOfAllPointsByEmail(email);
  }

  @Override
  public int totalRewardPerCustomerByMobile(String mobile) {
    return transactionRepository.sumOfAllPointsByMobile(mobile);
  }

  @Override
  public TotalRewardPoints totalRewardPoints(String mobileNumber, Long numberOfMonths) {
    TotalRewardPoints totalRewardPoints = new TotalRewardPoints();
    ArrayList<MonthlyRewards> monthlyRewardsList = new ArrayList<>();
    BigDecimal calculatedPoints = BigDecimal.ZERO;
    final LocalDate fromDate = LocalDate.now().minusMonths(numberOfMonths);
    Date date = Date.from(fromDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    final List<Object[]> customerRewards =
        transactionRepository.rewardPointsForCustomer(mobileNumber, date);
    for (Object[] localReward : customerRewards) {
      MonthlyRewards monthlyRewards = new MonthlyRewards();
      monthlyRewards.setMonth(String.valueOf(localReward[0]));
      monthlyRewards.setYear(String.valueOf(localReward[1]));
      final BigDecimal pointsForMonth = (BigDecimal) localReward[2];
      monthlyRewards.setRewardPoints(pointsForMonth);
      calculatedPoints = calculatedPoints.add(pointsForMonth);
      monthlyRewardsList.add(monthlyRewards);
      totalRewardPoints.setMonthlyRewardPoints(monthlyRewardsList);
      totalRewardPoints.setTotalRewardPoints(calculatedPoints);
    }

    return totalRewardPoints;
  }

  @Override
  public int refundTxn(Long txnId) {
    return transactionRepository.updateTxnRefund(txnId);
  }

  @Override
  public int rejectTxn(Long txnId) {
    return transactionRepository.updateTxnReject(txnId);
  }

  private Transaction convertDtoToEntity(TransactionData transactionDataDto)
      throws DataFormatException {
    Transaction transaction = new Transaction();
    transaction.setAmount(transactionDataDto.getAmount());

    final Optional<Customer> byId = customerRepo.findById(transactionDataDto.getCustId());
    if (byId.isPresent()) {
      transaction.setCustomer(byId.get());
    } else {
      throw new DataFormatException("Customer Can not be Found!");
    }

    transaction.setLocation(transactionDataDto.getLocation());
    transaction.setParticulars(transactionDataDto.getParticulars());
    transaction.setPoints(transactionDataDto.getPoints());
    transaction.setRefund(transactionDataDto.isRefund());
    transaction.setStatus(transactionDataDto.getStatus());
    transaction.setTime(transactionDataDto.getTime());
    return transaction;
  }

  private BigDecimal calculatePoints(BigDecimal purchaseAmount) {
    final List<RewardPoints> loyaltyPoints = pointsRepo.findAllByOrderByPurchaseAmountDesc();
    BigDecimal totalPoints = new BigDecimal(0);
    if (loyaltyPoints != null && !loyaltyPoints.isEmpty()) {
      BigDecimal points = BigDecimal.ZERO;
      for (RewardPoints point : loyaltyPoints) {
        BigDecimal amountLimit = point.getPurchaseAmount();
        if (purchaseAmount.compareTo(amountLimit) == 1
            || purchaseAmount.compareTo(amountLimit) == 0) {

          final BigDecimal pointPerCent =
              BigDecimal.valueOf(point.getPoints()).divide(BigDecimal.valueOf(100));
          log.debug("Current Points value - {}", pointPerCent);
          final BigDecimal subtract = purchaseAmount.subtract(amountLimit);
          points = subtract.multiply(pointPerCent).multiply(BigDecimal.valueOf(100));
          totalPoints = totalPoints.add(points);
          purchaseAmount = amountLimit;
        }
        log.info("Calcualted Points {} for the Purchase Amount {}", totalPoints, purchaseAmount);
      }
      return totalPoints;
    } else {
      return totalPoints;
    }
  }
}
