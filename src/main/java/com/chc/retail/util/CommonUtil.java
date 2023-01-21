package com.chc.retail.util;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CommonUtil {

  /*public static void main(String[] args) {
    // String s = "sai2222222kumar@yahoomail.com";
    // System.out.println(s.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*"));
    // System.out.println(maskMobile("8888888888367"));
    // System.out.println("Password " + new BCryptPasswordEncoder().encode("password"));
  }*/

  @PostConstruct
  void initializeBean() {
    System.out.println("Initialization of Common Util Completed !");
  }

  public String maskMobile(String mobile) {
    return mobile.replaceAll("(?<!^\\+)[0-9](?=[0-9]{4})", "*");
  }

  public String maskEmail(String email) {
    return email.replaceAll("(^[^@]{3}|(?!^)\\G)[^@]", "$1*");
  }
}
