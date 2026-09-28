package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    public enum Status { PENDING, IN_QUEUE, PREPARING, COMPLETED }

    private final int orderNumber;
    private final String customer;
    private final List<OrderItem> items;
    private Status status;
    private final LocalDateTime createdAt;

    public Order(int orderNumber, String customer, List<OrderItem> items) {
        this.orderNumber = orderNumber;
        this.customer = customer;
        this.items = new ArrayList<>(items);
        this.status = Status.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public int getOrderNumber() { return orderNumber; }
    public String getCustomer() { return customer; }
    public List<OrderItem> getItems() { return items; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public double getTotal() {
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public String getItemsSummary() {
        StringBuilder sb = new StringBuilder();
        for (OrderItem item : items) {
            if (sb.length() > 0) sb.append(", ");
            sb.append(item.getProduct().getName()).append(" x").append(item.getQuantity());
        }
        return sb.toString();
    }

    public String getTime() {
        return createdAt.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
