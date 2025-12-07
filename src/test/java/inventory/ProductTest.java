package inventory;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProductTest {

    @Test
    public void testIncreaseStock() {
        Product p = new Product("1", "Masa", 100.0, 5, 2);
        p.increaseStock(3);
        assertEquals(8, p.getStock());
    }

    @Test
    public void testDecreaseStock() {
        Product p = new Product("2", "Sandalye", 50.0, 10, 3);
        p.decreaseStock(4);
        assertEquals(6, p.getStock());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDecreaseStock_NotEnough() {
        Product p = new Product("3", "Dolap", 500.0, 2, 1);
        p.decreaseStock(5); // Yetersiz stok
    }

    @Test
    public void testSetStock() {
        Product p = new Product("4", "Koltuk", 700.0, 3, 1);
        p.setStock(15);
        assertEquals(15, p.getStock());
    }
}
