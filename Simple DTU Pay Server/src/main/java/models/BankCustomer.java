package models;

public class BankCustomer {

    String id, accNumber;

    public BankCustomer() {}

    public BankCustomer(String id, String accNumber) {
        this.id = id;
        this.accNumber = accNumber;
    }

    public String getId() {
        return id;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAccNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
