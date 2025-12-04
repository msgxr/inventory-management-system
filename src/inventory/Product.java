package inventory;

public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;
    private int minStockLevel;

    /**
     * Creates a new Product instance.
     * @param id unique product ID
     * @param name product name
     * @param price product price
     * @param stock initial stock level
     * @param minStockLevel minimum stock threshold
     */
    public Product(String id, String name, double price, int stock, int minStockLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minStockLevel = minStockLevel;
    }

    /**
     * Gets the unique product ID.
     * @return product ID
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the product name.
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the product price.
     * @return price value
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the current stock amount.
     * @return stock value
     */
    public int getStock() {
        return stock;
    }

    /**
     * Gets the minimum stock level threshold.
     * @return minimum stock value
     */
    public int getMinStockLevel() {
        return minStockLevel;
    }

    /**
     * Updates the stock value.
     * @param stock new stock value
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock cannot be negative");
        }
        this.stock = stock;
    }

    /**
     * Increases the stock by a given amount.
     * @param amount stock increase value
     */
    public void increaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative");
        }
        this.stock += amount;
    }

    /**
     * Decreases the stock by a given amount.
     * @param amount stock decrease value
     */
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
