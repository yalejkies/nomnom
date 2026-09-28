package model;

public class Product {
    private final int id;
    private final String name;
    private final String category;
    private final double price;
    private int stock;

    public Product(int id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setStock(int stock) { this.stock = Math.max(0, stock); }

    public boolean reduceStock(int qty) {
        if (qty <= 0 || stock < qty) return false;
        stock -= qty;
        return true;
    }

    public void addStock(int qty) {
        if (qty > 0) stock += qty;
    }

    public String getStockStatus() {
        return stock <= 2 ? "LOW" : "OK";
    }
}
