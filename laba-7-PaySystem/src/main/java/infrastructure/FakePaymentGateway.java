package infrastructure;

import domain.*;
import java.util.HashMap;
import java.util.Map;


public class FakePaymentGateway implements PaymentGateway {
    private boolean shouldSucceed = true;

    public void setShouldSucceed(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    @Override
    public boolean charge(String orderId, Money amount) {
        System.out.println("Processing payment for order " + orderId + ": " + amount);
        return shouldSucceed;
    }
}
