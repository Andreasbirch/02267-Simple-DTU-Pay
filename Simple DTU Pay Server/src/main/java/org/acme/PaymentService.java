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
    public ResponseMessage pay(Payment payment){
        String customerAccNumber = getAccNumberFromCpr(payment.getCid());
        String merchantAccNumber = getAccNumberFromCpr(payment.getMid());
        try {
            bankService.transferMoneyFromTo(customerAccNumber, merchantAccNumber, BigDecimal.valueOf(payment.getAmount()), "Payment from " + payment.getCid() + " to " + payment.getMid());
            return new ResponseMessage(true, "");
        } catch (BankServiceException_Exception e) {
            return new ResponseMessage(false, e.getMessage());
        }
//        if (!customers.contains(payment.getCid()) && !merchants.contains(payment.getMid())) {
//            return new ResponseMessage(false, "customer with id " + payment.getCid() + " and merchant with id " + payment.getMid() + " are unknown");
//        } else if (!customers.contains(payment.getCid())) {
//            return new ResponseMessage(false, "customer with id " + payment.getCid() + " is unknown");
//        } else if (!merchants.contains(payment.getMid())) {
//            return new ResponseMessage(false, "merchant with id " + payment.getMid() + " is unknown");
//        } else {
//        }
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

    public List<Transaction> getAll(String accNum) throws BankServiceException_Exception {
        Account account = bankService.getAccount(accNum);
        return account.getTransactions();
    }
}
