package org.acme;

import models.Payment;

import java.util.ArrayList;
import java.util.List;

public class Db {
    private static Db single_instance = null;

    // Declaring a variable of type String
    public static List<String> customerIds;
    public static List<Payment> payments;
    // Constructor of this class
    // Here private constructor is used to
    // restricted to this class itself
    private Db()
    {
        customerIds = new ArrayList<>();
        payments = new ArrayList<>();
    }

    // Method
    // Static method to create instance of Singleton class
    public static Db Db()
    {
        // To ensure only one instance is created
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