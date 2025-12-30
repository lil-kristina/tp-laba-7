package infrastructure;

import domain.*;
import java.util.HashMap;
import java.util.Map;


public class InMemoryOrderRepository implements OrderRepository {
    private final Map<String, Order> orders = new HashMap<>();

    @Override
    public Order getById(String orderId) {
        return orders.get(orderId);
    }

    @Override
    public void save(Order order) {
        orders.put(order.getId(), order);
    }
}
