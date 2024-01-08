package org.acme;

import dtu.ws.fastmoney.*;
import dtu.ws.fastmoney.BankService;
import models.Payment;
import models.ResponseMessage;

import java.math.BigDecimal;
import java.util.*;

public class PaymentService {
    // Lists used as "database"
    BankService bankService = new BankServiceService().getBankServicePort();
    List<Payment> payments = new ArrayList<>();

    public PaymentService() {}

    // Payment method checks for known customers and merchants
    // Adds payment if successful
    public ResponseMessage pay(Payment payment){
        Db db = Db.Db();
        //Customer and merchant not found
        if (!db.customerIds.contains(payment.getCid()) && !db.customerIds.contains(payment.getMid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " and merchant with id " + payment.getMid() + " are unknown");
        }

            //Customer not found
        if (!db.customerIds.contains(payment.getCid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " is unknown");
        }

        //Merchant not found
        if(!db.customerIds.contains(payment.getMid())) {
            return new ResponseMessage(false, "merchant with id " + payment.getMid() + " is unknown");
        }

        String customerAccNumber = getAccNumberFromCpr(payment.getCid());
        String merchantAccNumber = getAccNumberFromCpr(payment.getMid());
        try {
            bankService.transferMoneyFromTo(customerAccNumber, merchantAccNumber, BigDecimal.valueOf(payment.getAmount()), "Payment from " + payment.getCid() + " to " + payment.getMid());
            payments.add(payment);
            return new ResponseMessage(true, "");
        } catch (BankServiceException_Exception e) {
            return new ResponseMessage(false, e.getMessage());
        }
    }

    private String getAccNumberFromCpr(String cpr) {
        for (AccountInfo info: bankService.getAccounts()) {
            User user = info.getUser();
            if (user.getCprNumber().equals(cpr)) {
                return info.getAccountId();
            }
        }
        return "";
    }

    public List<Payment> getAll(){
        System.out.println("No. of payments: " + payments.size());
        return payments;
    }
}
