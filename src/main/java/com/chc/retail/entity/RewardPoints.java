package com.chc.retail.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table
@Data
public class RewardPoints {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "rewardpoint_seq", initialValue = 1)
  private Long serialNum;

  private BigDecimal purchaseAmount;
  private int points;
}
