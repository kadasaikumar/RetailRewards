package com.chc.retail.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CustomerData {

  @NotNull private String firstName;
  private String lastName;

  @NotNull
  @Email(regexp = ".+[@].+[\\.].+")
  private String email;

  @NotNull
  @Min(10)
  private String mobile;
}
