package org.acme;

import models.Payment;
import models.ResponseMessage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PaymentService {
    // Lists used as "database"
    ArrayList<Payment> payments = new ArrayList<>();
    Set<String> customers = new HashSet<>();
    Set<String> merchants = new HashSet<>();

    public PaymentService() {
        customers.add("cid1");
        merchants.add("mid1");
    }

    // Payment method checks for known customers and merchants
    // Adds payment if successful
    public ResponseMessage pay(Payment payment) {
        if (!customers.contains(payment.getCid()) && !merchants.contains(payment.getMid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " and merchant with id " + payment.getMid() + " are unknown");
        } else if (!customers.contains(payment.getCid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " is unknown");
        } else if (!merchants.contains(payment.getMid())) {
            return new ResponseMessage(false, "merchant with id " + payment.getMid() + " is unknown");
        } else {
            payments.add(payment);
            return new ResponseMessage(true, "");
        }
    }

    public ArrayList<Payment> getAll() {
        return payments;
    }
}
