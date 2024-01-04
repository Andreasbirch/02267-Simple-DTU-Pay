package dtu.example;

import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import models.Payment;

public class SimpleDTUPay {

    private Client client;
    WebTarget baseUrl;

    public SimpleDTUPay() {
        client = ClientBuilder.newClient();
        baseUrl = client.target("http://localhost:8080/");
    }

    public boolean pay(int amount, String cid, String mid) {
        Payment payment = new Payment(amount, cid, mid);
        baseUrl.path("payment")
                .request()
                .post(Entity.entity(payment, MediaType.APPLICATION_JSON));
        return true;
    }
}
