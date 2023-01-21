package com.chc.retail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table
@Data
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "transaction_seq", initialValue = 1)
  @Column(name = "transaction_id")
  private Long id;

  private String particulars;
  private BigDecimal amount;
  private int
      status; /* 1 - Accepted 2- Rejected 3 - Deemed Accepted 4- Refund initiated 5- Refund Completed**/
  private boolean refund;
  private Date time;
  private String location;
  private BigDecimal points;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id")
  private Customer customer;
  @JsonIgnore
  @Transient private Long total;
}
