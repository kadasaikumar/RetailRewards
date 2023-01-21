package com.chc.retail.repository;

import com.chc.retail.entity.RewardPoints;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface RewardPointsRepo extends JpaRepository<RewardPoints, BigDecimal> {

  List<RewardPoints> findAllByOrderByPurchaseAmountDesc();
}
