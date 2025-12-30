package domain;

// Value Object для денег
public class Money {
    private final double amount;

    public Money(double amount) {
        if (amount < 0) throw new IllegalArgumentException("Money cannot be negative");
        this.amount = Math.round(amount * 100.0) / 100.0;
    }

    public double getAmount() { return amount; }

    public Money add(Money other) {
        return new Money(this.amount + other.amount);
    }

    @Override
    public String toString() {
        return String.format("$%.2f", amount);
    }
}
