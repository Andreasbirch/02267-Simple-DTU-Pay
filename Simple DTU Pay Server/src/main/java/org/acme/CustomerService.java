package org.acme;

import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import models.BankCustomer;
import models.ResponseMessage;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    List<BankCustomer> customers = new ArrayList<>();
    BankService bank = new BankServiceService().getBankServicePort();
    public CustomerService() {}

    public ResponseMessage registerCustomer(BankCustomer customer) {
        //If customer already exists
        for (BankCustomer c: customers) {
            if (c.getId().equals(customer.getId())) {
                return new ResponseMessage(false, "Customer already exists.");
            }
        }

        if (!customerIsRegisteredWithBank(customer)) {
            return new ResponseMessage(false, "Customer is not registered with bank.");
        }

        customers.add(customer);
        return new ResponseMessage(true, "Success.");
    }

    private boolean customerIsRegisteredWithBank(BankCustomer customer) {
        for (AccountInfo info: bank.getAccounts()) {
            User user = info.getUser();
            if (user.getCprNumber().equals(customer.getId()) && info.getAccountId().equals(customer.getAccNumber())) {
                return true;
            }
        }
        return false;
    }

    public List<BankCustomer> getCustomers() {
        return customers;
    }

    public BankCustomer getCustomer(String customerId) {
        for (BankCustomer c: customers) {
            if (c.getId().equals(customerId)) {
                return c;
            }
        }
        return null;
    }
}
