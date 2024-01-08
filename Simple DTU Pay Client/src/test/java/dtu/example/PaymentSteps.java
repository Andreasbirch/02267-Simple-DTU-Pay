package dtu.example;

import dtu.ws.fastmoney.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Payment;
import models.RequestMessage;
import models.ResponseMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    Payment payment = new Payment();
    List<Payment> payments = new ArrayList<>();
    ResponseMessage responseMessage = new ResponseMessage();
    BankService bank = new BankServiceService().getBankServicePort();
    String accountNumCustomer, accountNumMerchant;
    boolean successful;

    User customer = new User();
    User merchant = new User();

    ArrayList<String> usedAccounts = new ArrayList<>();

    @Given("a successful payment of {int} kr from customer to merchant")
    public void aSuccessfulPaymentFromCustomerToMerchant(int amount){
        responseMessage = dtuPay.pay(amount, cid, mid);
        assertTrue(responseMessage.isSuccessful());
    }
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        responseMessage = dtuPay.pay(amount, cid, mid);
    }
    @When("the manager asks for a list of payments")
    public void theManagerAsksForAListOfPayments() {
        payments = dtuPay.getPayments();
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(responseMessage.isSuccessful());
    }

    @Then("the list contains a payments where customer paid {int} kr to merchant")
    public void theListContainsAPaymentsWhereCustomerPaidKrToMerchant(int amount) {
        boolean elementFound = false;
        for (Payment p : payments) {
            if (p.getAmount() == amount && p.getCid().equals(cid) && p.getMid().equals(mid)) {
                elementFound = true;
                break;
            }
        }
        assertTrue(elementFound);
    }

    @Then("the payment is not successful")
    public void thePaymentIsNotSuccessful() {
        assertFalse(responseMessage.isSuccessful());
    }

    @And("an error message is returned saying {string}")
    public void anErrorMessageIsReturnedSaying(String error) {
        assertEquals(error, responseMessage.getMessage());
    }

    @Given("a customer with a bank account with balance {int}")
    public void aCustomerWithABankAccountWithBalance(int balance) throws BankServiceException_Exception {
        customer = new User();
        customer.setCprNumber("1122330000");
        cid = customer.getCprNumber();
        customer.setFirstName("Alice");
        customer.setLastName("Aname");
        accountNumCustomer = bank.createAccountWithBalance(customer, BigDecimal.valueOf(balance));

        usedAccounts.add(accountNumCustomer);
    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDTUPay() {
        RequestMessage rm = new RequestMessage(customer.getFirstName(), customer.getLastName(), customer.getCprNumber(), accountNumCustomer);
        ResponseMessage response = dtuPay.registerCustomer(rm);
        successful = response.isSuccessful();

        responseMessage.setSuccessful(successful);
    }

    @Given("a merchant with a bank account with balance {int}")
    public void aMerchantWithABankAccountWithBalance(int balance) throws BankServiceException_Exception {
        merchant = new User();
        merchant.setCprNumber("3322119999");
        mid = merchant.getCprNumber();
        merchant.setFirstName("Bob");
        merchant.setLastName("Bname");
        accountNumMerchant = bank.createAccountWithBalance(merchant, BigDecimal.valueOf(balance));
        usedAccounts.add(accountNumMerchant);
    }

    @And("that the merchant is registered with DTU Pay")
    public void thatTheMerchantIsRegisteredWithDTUPay() {
        successful = dtuPay.registerCustomer(new RequestMessage(merchant.getFirstName(), merchant.getLastName(), merchant.getCprNumber(), accountNumMerchant)).isSuccessful();
        responseMessage.setSuccessful(successful);
    }

    @And("the balance of the customer at the bank is {int} kr")
    public void theBalanceOfTheCustomerAtTheBankIsKr(int newBalance) {
        try {
            assertEquals(BigDecimal.valueOf(newBalance), bank.getAccount(accountNumCustomer).getBalance());
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @And("the balance of the merchant at the bank is {int} kr")
    public void theBalanceOfTheMerchantAtTheBankIsKr(int newBalance) {
        try {
            assertEquals(BigDecimal.valueOf(newBalance), bank.getAccount(accountNumMerchant).getBalance());
        } catch (BankServiceException_Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Then("the payment is unsuccessful")
    public void thePaymentIsUnsuccessful() {

        assertFalse(responseMessage.isSuccessful());
    }

    @After
    public void after() throws BankServiceException_Exception {
        //Retire used accounts in bank
        for (String account: usedAccounts) {
            bank.retireAccount(account);
        }

        dtuPay.retireCustomer(customer.getCprNumber());
        dtuPay.retireCustomer(merchant.getCprNumber());
    }
}