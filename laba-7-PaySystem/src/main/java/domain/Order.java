package domain;

import java.util.ArrayList;
import java.util.List;

// Агрегат заказа
public class Order {
    private final String id;
    private final String customerId;
    private OrderStatus status;
    private final List<OrderItem> items;

    public Order(String id, String customerId) {
        this.id = id;
        this.customerId = customerId;
        this.status = OrderStatus.CREATED;
        this.items = new ArrayList<>();
    }

    // Бизнес-правила

    public void addItem(String productId, String name, Money price, int quantity) {
        if (isPaid()) throw new IllegalStateException("Cannot modify paid order");
        items.add(new OrderItem(productId, name, price, quantity));
    }

    public void pay() {
        if (items.isEmpty()) throw new IllegalStateException("Cannot pay empty order");
        if (isPaid()) throw new IllegalStateException("Order already paid");
        status = OrderStatus.PAID;
    }

    public Money calculateTotal() {
        Money total = new Money(0);
        for (OrderItem item : items) {
            total = total.add(item.getTotal());
        }
        return total;
    }

    // Геттеры
    public String getId() {
        return id;
    }
    public String getCustomerId() {
        return customerId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public boolean isPaid() {
        return status == OrderStatus.PAID;
    }
    public List<OrderItem> getItems() {
        return new ArrayList<>(items);
    }

    // Value Object для товара в заказе
    private static class OrderItem {
        private final String productId;
        private final String name;
        private final Money price;
        private final int quantity;

        public OrderItem(String productId, String name, Money price, int quantity) {
            this.productId = productId;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public Money getTotal() {
            return new Money(price.getAmount() * quantity);
        }
    }
}