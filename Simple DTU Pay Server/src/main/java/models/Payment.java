package models;

public class Payment {
    int amount;
    // CustomerId and MerchantId
    String cid, mid;

    public Payment() {

    }

    // Construct single payment
    public Payment(int amount, String cid, String mid) {
        this.amount = amount;
        this.cid = cid;
        this.mid = mid;
    }
}
