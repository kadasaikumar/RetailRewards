package com.chc.retail.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@ToString
public class AppUser {

  @Id
  @Column(name = "user_id")
  private Long id;

  @NotNull private String username;
  @NotNull private String password;
  @NotNull private boolean enabled;
  private String role;
}
