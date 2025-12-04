package inventory;

public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;
    private int minStockLevel;

    public Product(String id, String name, double price, int stock, int minStockLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minStockLevel = minStockLevel;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public int getMinStockLevel() {
        return minStockLevel;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;
    }

    public void increaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.stock += amount;
    }

    public void decreaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        if (amount > stock) {
            throw new IllegalArgumentException("Not enough stock");
        }
        this.stock -= amount;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name +
                " | price=" + price +
                " | stock=" + stock +
                " (min=" + minStockLevel + ")";
    }
}
