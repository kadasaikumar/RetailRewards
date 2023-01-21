package com.chc.retail.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class LoginDto {

  @JsonProperty private String userName;
  @JsonProperty private String password;
}
