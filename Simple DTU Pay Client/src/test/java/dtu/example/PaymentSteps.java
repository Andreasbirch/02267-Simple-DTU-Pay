package dtu.example;

import dtu.ws.fastmoney.*;
import io.cucumber.java.AfterAll;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.BankCustomer;
import models.Payment;
import models.RequestMessage;
import models.ResponseMessage;
import org.junit.After;
import org.junit.Before;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    Payment payment = new Payment();
    List<Transaction> payments = new ArrayList<>();
    ResponseMessage responseMessage = new ResponseMessage();
    BankService bank = new BankServiceService().getBankServicePort();
    String accountNumCustomer, accountNumMerchant;
    boolean successful;

    User customer = new User();
    User merchant = new User();

    @BeforeAll
    public void before() throws BankServiceException_Exception {
        ArrayList<Integer> indices = new ArrayList<>();
        int idx = 0;

        //Retire accounts
        for (AccountInfo info: bank.getAccounts()) {
            String accountId = info.getAccountId();
            if (bank.getAccount(accountId) != null) {
                bank.retireAccount(accountId);
                indices.add(idx);
                idx++;
            }
        }

        //Remove all retired accounts from list of accounts
        for (Integer i: indices) {
            bank.getAccounts().remove(i);
        }
    }

    //TODO We're fairly certain most of the original tests needs to be rewritten to support the bank extension.
    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }
    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentFromCustomerToMerchant(int amount, String cid, String mid) {
        if (dtuPay.pay(amount, cid, mid).isSuccessful()) {
            payment.setAmount(amount);
            payment.setCid(cid);
            payment.setMid(mid);
        }
    }
    @Given("a merchant with id {string}")
    public void aMerchantWithId(String mid) {
        this.mid = mid;
    }
    @When("the merchant initiates a payment for {int} kr by the customer")
    public void theMerchantInitiatesAPaymentForKrByTheCustomer(int amount) {
        responseMessage = dtuPay.pay(amount, cid, mid);
    }
    @When("the manager asks for a list of payments")
    public void theManagerAsksForAListOfPayments() {
        payments = dtuPay.getPayments(accountNumCustomer);
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(responseMessage.isSuccessful());
    }

    @Then("the list contains a payments where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsAPaymentsWhereCustomerPaidKrToMerchant(String cid, int amount, String mid) {
        boolean elementFound = false;
        for (Transaction p : payments) {
            if (p.getAmount().equals(BigDecimal.valueOf(amount)) && p.getDebtor().equals(cid) && p.getCreditor().equals(mid)) {
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
        customer.setCprNumber("1234567890");
        customer.setFirstName("Alice");
        customer.setLastName("Aname");
        accountNumCustomer = bank.createAccountWithBalance(customer, BigDecimal.valueOf(balance));
    }

    @And("that the customer is registered with DTU Pay")
    public void thatTheCustomerIsRegisteredWithDTUPay() {
        successful = dtuPay.registerCustomer(new RequestMessage(customer.getFirstName(), customer.getLastName(), customer.getCprNumber(), accountNumCustomer)).isSuccessful();
        responseMessage.setSuccessful(successful);
    }

    @Given("a merchant with a bank account with balance {int}")
    public void aMerchantWithABankAccountWithBalance(int balance) throws BankServiceException_Exception {
        merchant = new User();
        merchant.setCprNumber("9876543210");
        merchant.setFirstName("Bob");
        merchant.setLastName("Bname");
        accountNumMerchant = bank.createAccountWithBalance(merchant, BigDecimal.valueOf(balance));
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

    @AfterAll
    @After
    public void after() throws BankServiceException_Exception {
        ArrayList<Integer> indices = new ArrayList<Integer>();
        int idx = 0;
        for (AccountInfo info: bank.getAccounts()) {
            String accountId = info.getAccountId();
            if (bank.getAccount(accountId) != null) {
                bank.retireAccount(accountId);
                indices.add(idx);
                idx++;
            }
        }

        for (Integer i: indices) {
            bank.getAccounts().remove(i);
        }
    }
}