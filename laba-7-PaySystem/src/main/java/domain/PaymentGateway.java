package domain;

public interface PaymentGateway {
    boolean charge(String orderId, Money amount);
}