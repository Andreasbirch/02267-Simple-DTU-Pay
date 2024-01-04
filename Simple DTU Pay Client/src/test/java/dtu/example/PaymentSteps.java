package dtu.example;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Payment;
import models.ResponseMessage;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    Payment payment = new Payment();
    ArrayList<Payment> payments = new ArrayList<>();
    ResponseMessage responseMessage = new ResponseMessage();
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
        payments = dtuPay.getPayments();
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(responseMessage.isSuccessful());
    }

    @Then("the list contains a payments where customer {string} paid {int} kr to merchant {string}")
    public void theListContainsAPaymentsWhereCustomerPaidKrToMerchant(String cid, int amount, String mid) {
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
}