package org.acme;

import dtu.ws.fastmoney.*;
import dtu.ws.fastmoney.BankService;
import models.Payment;
import models.ResponseMessage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentService {
    // Lists used as "database"
    Set<String> customers = new HashSet<>();
    Set<String> merchants = new HashSet<>();
    BankService bankService = new BankServiceService().getBankServicePort();

    public PaymentService() {
        customers.add("cid1");
        merchants.add("mid1");
    }

    // Payment method checks for known customers and merchants
    // Adds payment if successful
    public ResponseMessage pay(Payment payment) throws BankServiceException_Exception {
        if (!customers.contains(payment.getCid()) && !merchants.contains(payment.getMid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " and merchant with id " + payment.getMid() + " are unknown");
        } else if (!customers.contains(payment.getCid())) {
            return new ResponseMessage(false, "customer with id " + payment.getCid() + " is unknown");
        } else if (!merchants.contains(payment.getMid())) {
            return new ResponseMessage(false, "merchant with id " + payment.getMid() + " is unknown");
        } else {
            bankService.transferMoneyFromTo(payment.getCid(), payment.getMid(), BigDecimal.valueOf(payment.getAmount()), "");
            return new ResponseMessage(true, "");
        }
    }

    public List<Transaction> getAll(String accNum) throws BankServiceException_Exception {
        Account account = bankService.getAccount(accNum);
        return account.getTransactions();
    }
}
