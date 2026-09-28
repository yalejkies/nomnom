package data;

import model.Order;
import model.OrderItem;
import model.Product;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AppData {
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static final List<Order> ORDERS = new ArrayList<>();
    public static final Queue<Order> ORDER_QUEUE = new LinkedList<>();
    private static final AtomicInteger ORDER_COUNTER = new AtomicInteger(1);

    static {
        PRODUCTS.add(new Product(1, "Mojito", "Cocktails", 120, 10));
        PRODUCTS.add(new Product(2, "Blue Lagoon", "Cocktails", 110, 5));
        PRODUCTS.add(new Product(3, "Margarita", "Cocktails", 130, 2));
        PRODUCTS.add(new Product(4, "Iced Coffee", "Non-Alcoholic", 90, 8));
        PRODUCTS.add(new Product(5, "Red Horse", "Beer", 80, 12));
        PRODUCTS.add(new Product(6, "San Mig Light", "Beer", 70, 10));
        PRODUCTS.add(new Product(7, "Tanduay Rum", "Shots", 60, 7));
        PRODUCTS.add(new Product(8, "Coke Float", "Non-Alcoholic", 90, 8));
    }

    public static Product findProduct(int id) {
        return PRODUCTS.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    public static Order createOrder(String customer, List<OrderItem> cart) {
        if (cart == null || cart.isEmpty()) return null;

        for (OrderItem item : cart) {
            if (item.getProduct().getStock() < item.getQuantity()) return null;
        }
        for (OrderItem item : cart) {
            item.getProduct().reduceStock(item.getQuantity());
        }

        Order order = new Order(ORDER_COUNTER.getAndIncrement(), customer, cart);
        order.setStatus(Order.Status.IN_QUEUE);
        ORDERS.add(order);
        ORDER_QUEUE.offer(order);
        return order;
    }

    public static void resetDemoData() {
        ORDERS.clear();
        ORDER_QUEUE.clear();
        ORDER_COUNTER.set(1);
        int[] stocks = {10,5,2,8,12,10,7,8};
        for (int i = 0; i < PRODUCTS.size(); i++) PRODUCTS.get(i).setStock(stocks[i]);
    }
}
