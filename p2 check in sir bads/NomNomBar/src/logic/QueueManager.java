package logic;

import data.AppData;
import model.Order;

public class QueueManager {
    public static Order peek() { return AppData.ORDER_QUEUE.peek(); }

    public static Order startNext() {
        Order order = AppData.ORDER_QUEUE.peek();
        if (order != null) order.setStatus(Order.Status.PREPARING);
        return order;
    }

    public static Order completeCurrent() {
        Order order = AppData.ORDER_QUEUE.poll();
        if (order != null) order.setStatus(Order.Status.COMPLETED);
        return order;
    }
}
