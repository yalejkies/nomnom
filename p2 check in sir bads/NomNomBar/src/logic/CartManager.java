package logic;

import model.OrderItem;
import model.Product;
import java.util.ArrayList;
import java.util.List;

public class CartManager {
    private final List<OrderItem> items = new ArrayList<>();

    public void add(Product product) {
        for (OrderItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                if (item.getQuantity() < product.getStock()) item.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        if (product.getStock() > 0) items.add(new OrderItem(product, 1));
    }

    public void changeQuantity(Product product, int delta) {
        for (OrderItem item : items) {
            if (item.getProduct().getId() == product.getId()) {
                int q = item.getQuantity() + delta;
                if (q <= 0) items.remove(item);
                else if (q <= product.getStock()) item.setQuantity(q);
                return;
            }
        }
    }

    public void remove(Product product) {
        items.removeIf(i -> i.getProduct().getId() == product.getId());
    }

    public List<OrderItem> getItems() { return new ArrayList<>(items); }
    public double getTotal() { return items.stream().mapToDouble(OrderItem::getSubtotal).sum(); }
    public int getCount() { return items.stream().mapToInt(OrderItem::getQuantity).sum(); }
    public void clear() { items.clear(); }
}
