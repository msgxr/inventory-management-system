package inventory;

import org.junit.Test;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

public class InventoryTest {

    @Test
    public void testAddProduct() {
        Inventory inv = new Inventory();
        Product p = new Product("P1", "Masa", 1500.0, 3, 1);
        inv.addProduct(p);

        assertEquals(1, inv.getAllProducts().size());
        assertEquals("Masa", inv.getAllProducts().get(0).getName());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddDuplicateProduct() {
        Inventory inv = new Inventory();
        inv.addProduct(new Product("P1", "Masa", 1000, 5, 1));
        inv.addProduct(new Product("P2", "masa", 1200, 4, 1)); // duplicate: masa/Masa
    }

    @Test
    public void testSearchProduct() {
        Inventory inv = new Inventory();
        inv.addProduct(new Product("P1", "Sandalye", 300, 10, 2));
        inv.addProduct(new Product("P2", "Masa", 1500, 5, 2));

        List<Product> result = inv.searchProduct("sa");

        assertEquals(2, result.size());
    }

    @Test
    public void testListLowStock() {
        Inventory inv = new Inventory();
        inv.addProduct(new Product("P1", "Koltuk", 700, 1, 2)); // low stock
        inv.addProduct(new Product("P2", "Dolap", 1200, 5, 2)); // normal

        List<Product> lowStock = inv.listLowStock();

        assertEquals(1, lowStock.size());
        assertEquals("Koltuk", lowStock.get(0).getName());
    }

    @Test
    public void testPerishableProduct() {
        Inventory inv = new Inventory();
        PerishableProduct pp = new PerishableProduct("P3", "Süt", 25, 4, 2, LocalDate.now().minusDays(1)); // expired
        inv.addProduct(pp);

        assertTrue(pp.isExpired());
    }
}
