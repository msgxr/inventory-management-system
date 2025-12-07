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
    public void testInvalidRestock() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500, 3, 1);

        inv.addProduct(p);

        // Supplier null olursa hata verir
        Order order = new Order(p, 5, null);
    }
}
