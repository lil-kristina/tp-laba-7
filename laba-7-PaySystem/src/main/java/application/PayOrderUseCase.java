package application;

import domain.*;

public class PayOrderUseCase {
    private final OrderRepository orderRepository;
    private final PaymentGateway paymentGateway;

    public PayOrderUseCase(OrderRepository orderRepository, PaymentGateway paymentGateway) {
        this.orderRepository = orderRepository;
        this.paymentGateway = paymentGateway;
    }

    public PaymentResult execute(String orderId) {
        //Загружаем заказ
        Order order = orderRepository.getById(orderId);
        if (order == null) {
            return new PaymentResult(false, "Order not found");
        }

        try {
            //Оплачиваем в домене
            order.pay();

            //Совершаем платеж
            Money total = order.calculateTotal();
            boolean paymentSuccess = paymentGateway.charge(orderId, total);

            if (paymentSuccess) {
                //Сохраняем
                orderRepository.save(order);
                return new PaymentResult(true, "Payment successful: " + total);
            } else {
                return new PaymentResult(false, "Payment gateway failed");
            }
        } catch (IllegalStateException e) {
            return new PaymentResult(false, e.getMessage());
        }
    }

    //Результат
    public static class PaymentResult {
        public final boolean success;
        public final String message;

        public PaymentResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        @Override
        public String toString() {
            return message;
        }
    }
}
