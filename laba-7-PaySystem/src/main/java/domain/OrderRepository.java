package domain;

public interface OrderRepository {
        Order getById(String orderId);
        void save(Order order);
    }

