package com.chc.retail.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class TotalRewardPoints {

  BigDecimal totalRewardPoints;
  List<MonthlyRewards> monthlyRewardPoints;
}
