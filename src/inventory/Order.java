package inventory;

public class Order {

    private Product product;
    private int quantity;
    private Supplier supplier;
    private boolean processed;

    public Order(Product product, int quantity, Supplier supplier) {
        this.product = product;
        this.quantity = quantity;
        this.supplier = supplier;
        this.processed = false;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void processOrder() {
        this.processed = true;
    }

    @Override
    public String toString() {
        return "Order{" +
                "product=" + product.getName() +
                ", quantity=" + quantity +
                ", supplier=" + supplier.getName() +
                ", processed=" + processed +
                '}';
    }
}
