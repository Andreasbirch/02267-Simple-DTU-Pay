package models;

public class RequestMessage {
    public String firstName, lastName, cpr, accNumber;

    public RequestMessage() {}

    public RequestMessage(String firstName, String lastName, String cpr, String accNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.cpr = cpr;
        this.accNumber = accNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getAccNumber() {
        return accNumber;
    }

    public void accNumber(String accNumber) {
        this.accNumber = accNumber;
    }
}
