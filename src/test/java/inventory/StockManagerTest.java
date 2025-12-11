package inventory;

import org.junit.Test;
import static org.junit.Assert.*;

public class StockManagerTest {

    @Test
    public void testCreateOrder() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500, 3, 1);
        Supplier s = new Supplier("S1", "ABC Tedarik", "abc@tedarik.com", "0555");

        inv.addProduct(p);
        Order order = new StockManager(inv).createOrder(p, 5, s);

        assertEquals(p, order.getProduct());
        assertEquals(5, order.getQuantity());
        assertEquals(s, order.getSupplier());
        assertFalse(order.isProcessed());
    }

    @Test
    public void testRestock() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500, 3, 1);
        Supplier s = new Supplier("S1", "ABC Tedarik", "abc@tedarik.com", "0555");

        inv.addProduct(p);

        Order order = new StockManager(inv).createOrder(p, 5, s);

        StockManager manager = new StockManager(inv);
        manager.restock(order);

        assertTrue(order.isProcessed());
        assertEquals(8, p.getStock()); // 3 + 5
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRestockWithNullOrderThrows() {
        Inventory inv = new Inventory();
        StockManager manager = new StockManager(inv);
        manager.restock(null);
    }

    @Test
    public void testMultipleRestockIncreasesStockAccumulatively() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500, 3, 1);
        Supplier s = new Supplier("S1", "ABC Tedarik", "abc@tedarik.com", "0555");

        inv.addProduct(p);

        StockManager manager = new StockManager(inv);

        Order o1 = manager.createOrder(p, 5, s);
        Order o2 = manager.createOrder(p, 2, s);

        manager.restock(o1);
        manager.restock(o2);

        assertTrue(o1.isProcessed());
        assertTrue(o2.isProcessed());
        assertEquals(10, p.getStock()); // 3 + 5 + 2
    }

    @Test
    public void testCreateOrderDoesNotModifyInventoryImmediately() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500, 3, 1);
        Supplier s = new Supplier("S1", "ABC Tedarik", "abc@tedarik.com", "0555");

        inv.addProduct(p);
        int initialStock = p.getStock();

        Order order = new StockManager(inv).createOrder(p, 7, s);

        // Creating an order should not change product stock until restock is called
        assertEquals(initialStock, p.getStock());
        assertFalse(order.isProcessed());
    }
}
