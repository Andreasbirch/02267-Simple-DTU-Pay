package models;

public class Payment {
    int amount;
    // CustomerId and MerchantId
    String cid, mid;

    public Payment() {}

    // Construct single payment
    public Payment(int amount, String cid, String mid) {
        this.amount = amount;
        this.cid = cid;
        this.mid = mid;
    }

    // Getters and setters
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }
}
