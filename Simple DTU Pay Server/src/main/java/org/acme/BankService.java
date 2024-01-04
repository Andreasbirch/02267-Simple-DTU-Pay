package org.acme;

import dtu.ws.fastmoney.Account;
import dtu.ws.fastmoney.BankServiceException_Exception;
import dtu.ws.fastmoney.BankServiceService;
import models.BankCustomer;

import java.math.BigDecimal;
import java.util.*;

public class BankService {

    List<BankCustomer> customers = new ArrayList<>();
    dtu.ws.fastmoney.BankService bankService = new BankServiceService().getBankServicePort();

    public BankService() {

    }
    public boolean registerCustomer(BankCustomer customer) {
        for (BankCustomer c: customers) {
            if (c.getId().equals(customer.getId())) {
                return false;
            }
        }
        customers.add(customer);
        return true;
    }

    public BigDecimal getAccountBalance(String accNum) throws BankServiceException_Exception {
        Account account = bankService.getAccount(accNum);
        return account.getBalance();
    }

    //Returns only the first element, to showcase how the get function does not work
    public BankCustomer getCustomer(String customerId) {
        return customers.get(0);
//        for (BankCustomer c: customers) {
//            if (c.getId().equals(customerId)) {
//                return c;
//            }
//        }
//        return null;
    }
}
