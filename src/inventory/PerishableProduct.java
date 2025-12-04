package inventory;

import java.time.LocalDate;

/**
 * Son kullanma tarihi olan ürünleri temsil eder.
 */
public class PerishableProduct extends Product {

    private LocalDate expirationDate;

    /**
     * Yeni bir bozulabilir ürün oluşturur.
     *
     * @param id ürün ID
     * @param name ürün adı
     * @param price fiyat
     * @param stock stok
     * @param minStockLevel minimum stok seviyesi
     * @param expirationDate son kullanma tarihi
     */
    public PerishableProduct(String id, String name, double price, int stock,
                             int minStockLevel, LocalDate expirationDate) {
        super(id, name, price, stock, minStockLevel);
        this.expirationDate = expirationDate;
    }

    /**
     * Ürünün son kullanma tarihinin geçip geçmediğini kontrol eder.
     *
     * @return son kullanma tarihi geçmişse true, değilse false
     */
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationDate);
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    @Override
    public String toString() {
        return super.toString() + " | exp=" + expirationDate;
    }
}
