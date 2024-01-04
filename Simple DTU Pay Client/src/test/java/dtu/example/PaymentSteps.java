package dtu.example;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import models.Payment;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PaymentSteps {
    String cid, mid;
    SimpleDTUPay dtuPay = new SimpleDTUPay();
    Payment payment = new Payment();
    ArrayList<Payment> payments = new ArrayList<>();
    boolean successful;
    @Given("a customer with id {string}")
    public void aCustomerWithId(String cid) {
        this.cid = cid;
    }
    @Given("a successful payment of {int} kr from customer {string} to merchant {string}")
    public void aSuccessfulPaymentFromCustomerToMerchant(int amount, String cid, String mid) {
        if (dtuPay.pay(amount, cid, mid)) {
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
        successful = dtuPay.pay(amount,cid,mid);
    }
    @When("the manager asks for a list of payments")
    public void theManagerAsksForAListOfPayments() {
        payments = dtuPay.getPayments();
    }

    @Then("the payment is successful")
    public void thePaymentIsSuccessful() {
        assertTrue(successful);
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
}