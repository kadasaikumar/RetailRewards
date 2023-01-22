# RetailRewards

RetailRewards Application will help us determine the how many Rewards points will be awarded for the customer based on Purchase amount of the transaction.

Reward Points will be calculated based on 
50$ above spent - 1 Point will be awarded
100$ above spent - 2 Points will be awarded

Will be awarded reward points for the additional cents spent also

Series of API's Exposed for the consumption. All the APIs was secured, have to authenticated to access the API

Customer:
---------
	Create a new Customer                  					- api/customer
	Retrieve Customer Based on Customer ID 					- api/customer/{custId}
	Retrieve Customer Based on Customer ID 					- api/customer/email/{email}
	Retrieve Customer Based on Customer ID 					- api/customer/mobile/{mobileNumber}
  
  
    Only User with Admin can Access:
    	List of all available Customers    					- api/customers
      Delete a Customer                  					- api/customer

Transaction:
-----------
For Simulating Realtime scenario providing an option for tracking rejected and refuded transactions

   Create a new Transaction for the user                            - /api/purchase/transaction
     
	 View the purchase history of the user based on Customer's email  - /api/purchase/history/email/{email}
	 View the purchase history of the user based on Customer's Id     - /api/purchase/history/{custId}	 
	 View the purchase history of the user based on Customer's email  - /api/purchase/history/mobile/{mobileNumber}
	 
   View All Rejected Transctions of the Customer                    - /api/purchase/history/rejected/{custId}
	 View All Refunded Transctions of the Customer                    - /api/purchase/history/refund/{custId}
     
	 View Total Points earned by the Transactions of the Customer     - /api/purchase/rewards/{custId}
	 View Total Points earned by the Transactions of the Customer     - /api/purchase/rewards/email/{email}
	 View Total Points earned by the Transactions of the Customer     - /api/purchase/rewards/mobile/{mobileNumber}
   Total Points earned by the Customer during last n months         - /api/purchase/rewards/{mobileNumber}/{numberOfMonths}
   API Mark the Transaction as Refunded                             - /api/purchase/refund/{txnId}
   API Mark the Transaction as Rejected                             - /api/purchase/reject/{txnId}
   
   Only User with Admin Role can Access:
   	View the purchase histtory of the user based on Customer's email - /api/purchase/history/{page}/{size}

Checkout and Run the application
--------------------------------

    git clone https://github.com/kadasaikumar/RetailRewards.git
    mvn clean install
    mvn spring-boot:run

Database:
---------
    http://localhost:8899/h2-ui
    JDBC URL:jdbc:h2:mem:rewards
    username : sa
    password : password
    
Authentication:
---------------
http://localhost:8899/auth

Using Spring Security All the end points was secured.

    
  Note: 
  -----
  Users Must be Authenticated and should be Autorized to Access the API 
  
  User Roles - Dev and Admin
  
  Dev users - sai,ram
  Admin Users - ram,kumar
  
  Default Password - password
  
  All Passwords for the user login will be encrypted with BCryptPasswordEncoder while storing in DB
  
  Asking is enabled for Mobilenumber and Email of the customer details - Admin can view unmasked details
  
  
  API Documentation:
  ------------------
  
  Detailed Documentation can be found at the location upon running the application 
  
    http://localhost:8899/v3/api-docs
  	
	  http://localhost:8899/v3/api-docs.yaml
  	
	  http://localhost:8899/swagger-ui.html	
  	
	  http://localhost:8899/swagger-ui/index.html#/
  	
	  http://localhost:8899/v3/api-docs/swagger-config
    
    NOTE : PostMan Collection and other Details can be found on docs folder
