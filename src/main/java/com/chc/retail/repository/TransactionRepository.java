package com.chc.retail.repository;

import com.chc.retail.entity.Transaction;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface TransactionRepository extends PagingAndSortingRepository<Transaction, Long> {

  List<Transaction> findByCustomer_CustId(Long custId);

  List<Transaction> findByCustomer_Email(String email);

  List<Transaction> findByCustomer_MobileNumber(String mobile);

  List<Transaction> findAllByOrderByTime(Pageable sortByTime);

  @Query(
      "SELECT sum(t.points) from Transaction t where t.status = 1 and t.status=2 and t.refund=false and t.customer.custId=?1")
  int sumOfAllPoints(Long customerId);

  @Query(
      "SELECT sum(t.points) from Transaction t where t.status = 1 and t.status=2 and t.refund=false and t.customer.mobileNumber=?1")
  int sumOfAllPointsByMobile(String mobileNumber);

  @Query(
      "SELECT sum(t.points) from Transaction t where t.status = 1 and t.status=2 and t.refund=false and t.customer.email=?1")
  int sumOfAllPointsByEmail(String email);

  List<Transaction> findByStatusEqualsAndCustomer_CustId(int status, Long custId);

  @Query(
      "SELECT MONTHNAME(tx.time) as month, YEAR(tx.time) as year, "
          + "sum(tx.points) from Transaction tx,Customer cust where "
          + "cust.custId = tx.customer.custId and cust.mobileNumber =?1 and tx.time >?2 and tx.status < 3 and tx.refund = false group by month, "
          + "year order by time desc")
  List<Object[]> rewardPointsForCustomer(String mobileNumber, Date fromDate);

  @Transactional
  @Modifying
  @Query("update Transaction t set  t.points= '0.0',t.status=3 where t.id=?1")
  int updateTxnReject(Long txnId);

  @Transactional
  @Modifying
  @Query("update Transaction t set  t.points= '0.0',t.refund=true where t.id=?1")
  int updateTxnRefund(Long txnId);
}
