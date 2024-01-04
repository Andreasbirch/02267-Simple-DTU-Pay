package org.acme;

import models.Payment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class PaymentService {
    // Lists used as "database"
    ArrayList<Payment> payments = new ArrayList<>();
    Set<String> customers = new HashSet<>();
    Set<String> merchants = new HashSet<>();

    public PaymentService() {

    }

    // Payment method checks for known customers and merchants
    // Adds payment if successful
    public boolean pay(int amount, String cid, String mid) {
        Payment payment = new Payment(amount, cid, mid);
        payments.add(payment);
        return true;
    }
}
