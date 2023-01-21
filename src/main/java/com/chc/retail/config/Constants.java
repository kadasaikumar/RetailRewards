package com.chc.retail.config;

public class Constants {

  /* Common Constants*/
  public static final String API = "api";
  public static final String PING = "/ping";
  public static final String API_PURCHASE = API + "/purchase";

  /*Transaction Controller Constants*/
  public static final String TRANSACTION = "/transaction";

  public static final String HISTORY_EMAIL = "/history/email/{email}";
  public static final String HISTORY_CUST_ID = "/history/{custId}";
  public static final String HISTORY_PAGE_SIZE = "/history/{page}/{size}";
  public static final String HISTORY_MOBILE_NUMBER = "/history/mobile/{mobileNumber}";
  public static final String HISTORY_REJECTED_CUST_ID = "/history/rejected/{custId}";
  public static final String HISTORY_REFUNDED_CUST_ID = "/history/refund/{custId}";
  public static final String REJECT_TXN_ID = "/reject/{txnId}";
  public static final String REFUND_TXN_ID = "/refund/{txnId}";

  public static final String REWARDS_CUST_ID = "/rewards/{custId}";
  public static final String REWARDS_MOBILE_NUMBER_Months =
      "/rewards/{mobileNumber}/{numberOfMonths}";
  public static final String REWARDS_MOBILE_NUMBER = "/rewards/{mobileNumber}";
  /*Customer Controller Constants*/
  public static final String CUSTOMER = "/customer";

  public static final String CUSTOMERS = "/customers";

  public static final String CUSTOMER_CUST_ID = "/customer/{custId}";
  public static final String CUSTOMER_EMAIL_EMAIL = "/customer/email/{email}";
  public static final String CUSTOMER_MOBILE_MOBILE_NUMBER = "/customer/mobile/{mobileNumber}";
}
