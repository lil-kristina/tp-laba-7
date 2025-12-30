import domain.*;
import application.*;
import infrastructure.*;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("=== Running Tests ===\n");

        test1_successfulPayment();
        test2_emptyOrder();
        test3_alreadyPaid();
        test4_paymentFails();

        System.out.println("\n=== All tests completed ===");
    }

    static void test1_successfulPayment() {
        System.out.println("Test 1: Successful payment");

        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        FakePaymentGateway gateway = new FakePaymentGateway();
        PayOrderUseCase useCase = new PayOrderUseCase(repo, gateway);

        Order order = new Order("TEST-1", "CUST-1");
        order.addItem("P1", "Item", new Money(100), 1);
        repo.save(order);

        var result = useCase.execute("TEST-1");
        assert result.success : "Should succeed";
        System.out.println("  " + result);
    }

    static void test2_emptyOrder() {
        System.out.println("\nTest 2: Empty order (should fail)");

        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        FakePaymentGateway gateway = new FakePaymentGateway();
        PayOrderUseCase useCase = new PayOrderUseCase(repo, gateway);

        Order order = new Order("TEST-2", "CUST-2");
        //Пустой заказ
        repo.save(order);

        var result = useCase.execute("TEST-2");
        assert !result.success : "Should fail - empty order";
        assert result.message.contains("empty") : "Should mention empty order";
        System.out.println("  " + result);
    }

    static void test3_alreadyPaid() {
        System.out.println("\nTest 3: Already paid (should fail)");

        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        FakePaymentGateway gateway = new FakePaymentGateway();
        PayOrderUseCase useCase = new PayOrderUseCase(repo, gateway);

        Order order = new Order("TEST-3", "CUST-3");
        order.addItem("P1", "Item", new Money(100), 1);
        repo.save(order);

        // Первая оплата - успешна
        useCase.execute("TEST-3");

        // Вторая оплата должна не получиться
        var result = useCase.execute("TEST-3");
        assert !result.success : "Should fail - already paid";
        assert result.message.contains("already") : "Should mention already paid";
        System.out.println("  " + result);
    }

    static void test4_paymentFails() {
        System.out.println("\nTest 4: Payment gateway fails");

        InMemoryOrderRepository repo = new InMemoryOrderRepository();
        FakePaymentGateway gateway = new FakePaymentGateway();
        gateway.setShouldSucceed(false); // Шлюз всегда фейлится
        PayOrderUseCase useCase = new PayOrderUseCase(repo, gateway);

        Order order = new Order("TEST-4", "CUST-4");
        order.addItem("P1", "Item", new Money(100), 1);
        repo.save(order);

        var result = useCase.execute("TEST-4");
        assert !result.success : "Should fail - gateway failed";
        assert result.message.contains("gateway") : "Should mention gateway";
        System.out.println("  " + result);
    }
}