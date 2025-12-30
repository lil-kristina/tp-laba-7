import domain.*;
import application.*;
import infrastructure.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("===Payment System Architecture===\n");

        // Создаем инфраструктуру
        InMemoryOrderRepository repository = new InMemoryOrderRepository();
        FakePaymentGateway paymentGateway = new FakePaymentGateway();

        // Создаем заказ
        Order order = new Order("ORDER-001", "CUSTOMER-123");
        order.addItem("P1", "Laptop", new Money(1000), 1);
        order.addItem("P2", "Mouse", new Money(50), 2);

        // Сохраняем заказ
        repository.save(order);
        System.out.println("Order created: " + order.calculateTotal());

        // Используем Use Case
        PayOrderUseCase useCase = new PayOrderUseCase(repository, paymentGateway);
        PayOrderUseCase.PaymentResult result = useCase.execute("ORDER-001");

        System.out.println("\nResult: " + result);

        // Пробуем оплатить еще раз (должно не получиться)
        System.out.println("\nTrying to pay again:");
        PayOrderUseCase.PaymentResult result2 = useCase.execute("ORDER-001");
        System.out.println("Result: " + result2);
    }
}

