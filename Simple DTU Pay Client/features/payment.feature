Feature: Payment
  Scenario: Successful Payment through bank
    Given a customer with a bank account with balance 1000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 2000
    And that the merchant is registered with DTU Pay
    When the merchant initiates a payment for 100 kr by the customer
    Then the payment is successful
    And the balance of the customer at the bank is 900 kr
    And the balance of the merchant at the bank is 2100 kr

  Scenario: List of payments
    Given a customer with a bank account with balance 1000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 2000
    And that the merchant is registered with DTU Pay
    Given a successful payment of 10 kr from customer to merchant
    When the manager asks for a list of payments
    Then the list contains a payments where customer paid 10 kr to merchant

  Scenario: Customer does not have enough money
    Given a customer with a bank account with balance 0
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 2000
    And that the merchant is registered with DTU Pay
    When the merchant initiates a payment for 100 kr by the customer
    Then the payment is unsuccessful
    And the balance of the customer at the bank is 0 kr
    And the balance of the merchant at the bank is 2000 kr

  Scenario: Customer is not known to DTUPay
    Given a customer with a bank account with balance 1000
    Given a merchant with a bank account with balance 2000
    And that the merchant is registered with DTU Pay
    When the merchant initiates a payment for 100 kr by the customer
    Then the payment is unsuccessful
    And an error message is returned saying "customer with id 1122330000 is unknown"

  Scenario: Merchant is not known to DTUPay
    Given a customer with a bank account with balance 1000
    And that the customer is registered with DTU Pay
    Given a merchant with a bank account with balance 2000
    When the merchant initiates a payment for 100 kr by the customer
    Then the payment is unsuccessful
    And an error message is returned saying "merchant with id 3322119999 is unknown"

  Scenario: Neither Customer nor Merchant are known to DTUPay
    Given a customer with a bank account with balance 1000
    Given a merchant with a bank account with balance 2000
    When the merchant initiates a payment for 100 kr by the customer
    Then the payment is unsuccessful
    And an error message is returned saying "customer with id 1122330000 and merchant with id 3322119999 are unknown"