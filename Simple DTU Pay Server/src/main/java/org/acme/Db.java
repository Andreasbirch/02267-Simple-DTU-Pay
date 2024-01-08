package org.acme;

import models.Payment;

import java.util.ArrayList;
import java.util.List;

public class Db {
    private static Db single_instance = null;

    public static List<String> customerIds;
    public static List<Payment> payments;

    private Db()
    {
        customerIds = new ArrayList<>();
        payments = new ArrayList<>();
    }

    // Static method to create instance of Singleton class
    public static Db Db()
    {
        if (single_instance == null) {
            single_instance = new Db();
        }
        return single_instance;
    }

    public static List<String> getCustomerIds() {
        return customerIds;
    }

    public static List<Payment> getPayments() {
        return payments;
    }
}