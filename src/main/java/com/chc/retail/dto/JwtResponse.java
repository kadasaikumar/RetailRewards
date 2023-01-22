package com.chc.retail.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtResponse {

  private String token;
  private String tokenHeader;

  public JwtResponse(String token) {
    this.token = token;
    this.tokenHeader = "Bearer";
  }
}
