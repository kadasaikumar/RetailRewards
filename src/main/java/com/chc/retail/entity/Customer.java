package com.chc.retail.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class Customer {

  @Id
  @Column(name = "customer_id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @SequenceGenerator(name = "customer_seq", initialValue = 1)
  private Long custId;

  private String firstName;
  private String lastName;

  private String email;

  @Column(name = "mobile", unique = true)
  private String mobileNumber;
}
