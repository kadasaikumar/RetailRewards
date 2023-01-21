package com.chc.retail.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
public class TransactionData {

  private String particulars;
  private BigDecimal amount;
  private int
      status; /* 1 - Accepted 2- Deemed Accepted 3 - Rejected 4- Refund Completed**/
  private boolean refund;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-mm-yyyy hh:mm:ss")
  private Date time;

  private String location;
  @JsonIgnore private BigDecimal points;
  @NotNull private long custId;
}
