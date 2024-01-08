package org.acme;

import dtu.ws.fastmoney.AccountInfo;
import dtu.ws.fastmoney.BankService;
import dtu.ws.fastmoney.BankServiceService;
import dtu.ws.fastmoney.User;
import models.BankCustomer;
import models.ResponseMessage;

import java.util.List;

public class CustomerService {
    BankService bank = new BankServiceService().getBankServicePort();
    public CustomerService() {}

    public ResponseMessage registerCustomer(BankCustomer customer) {
        Db db = Db.Db();
        //If customer already exists
        for (String c: db.customerIds) {
            if (c.equals(customer.getId())) {
                return new ResponseMessage(false, "Customer already exists.");
            }
        }

        if (!customerIsRegisteredWithBank(customer)) {
            return new ResponseMessage(false, "Customer is not registered with bank.");
        }

        db.customerIds.add(customer.getId());
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

    public List<String> getCustomers() {
        Db db = Db.Db();
        return db.customerIds;
    }

    public String getCustomer(String customerId) {
        Db db = Db.Db();
        for (String c: db.customerIds) {
            if (c.equals(customerId)) {
                return c;
            }
        }
        return null;
    }

    public void removeCustomer(String customerId) {
        Db db = Db.Db();
        db.customerIds.remove(customerId);
    }
}
