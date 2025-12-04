package inventory;

public class StockManager {

    private Inventory inventory;

    public StockManager(Inventory inventory) {
        this.inventory = inventory;
    }

    public Order createOrder(Product product, int quantity, Supplier supplier) {
        return new Order(product, quantity, supplier);
    }

    public void restock(Order order) {
        Product product = order.getProduct();
        product.increaseStock(order.getQuantity());
        order.processOrder();
    }
}
