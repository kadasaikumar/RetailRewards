package com.chc.retail.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MonthlyRewards {

  String month;
  String year;
  BigDecimal rewardPoints;
}
