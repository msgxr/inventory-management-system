package inventory;

/**
 * Ürün bilgilerini tutan temel sınıftır.
 * Ürün kimliği, adı, fiyatı, stok miktarı ve minimum stok seviyesi gibi
 * özellikleri kapsülleyerek yönetir.
 */
public class Product {

    private String id;
    private String name;
    private double price;
    private int stock;
    private int minStockLevel;

    /**
     * Yeni bir Product nesnesi oluşturur.
     *
     * @param id ürünün benzersiz kimliği
     * @param name ürün adı
     * @param price ürün fiyatı
     * @param stock başlangıç stok değeri
     * @param minStockLevel minimum stok eşiği
     */
    public Product(String id, String name, double price, int stock, int minStockLevel) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.minStockLevel = minStockLevel;
    }

    /** @return ürün ID */
    public String getId() {
        return id;
    }

    /** @return ürün adı */
    public String getName() {
        return name;
    }

    /** @return ürün fiyatı */
    public double getPrice() {
        return price;
    }

    /** @return stok miktarı */
    public int getStock() {
        return stock;
    }

    /** @return minimum stok seviyesi */
    public int getMinStockLevel() {
        return minStockLevel;
    }

    /**
     * Stok değerini günceller.
     *
     * @param stock yeni stok değeri
     */
    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stok negatif olamaz.");
        }
        this.stock = stock;
    }

    /**
     * Stoku belirtilen miktarda artırır.
     *
     * @param amount artırılacak miktar
     */
    public void increaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Artış miktarı negatif olamaz.");
        }
        this.stock += amount;
    }

    /**
     * Stoku belirtilen miktarda azaltır.
     *
     * @param amount azaltılacak miktar
     */
    public void decreaseStock(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Azaltma miktarı negatif olamaz.");
        }
        if (amount > stock) {
            throw new IllegalArgumentException("Yeterli stok yok.");
        }
        this.stock -= amount;
    }

    /**
     * Ürün bilgilerini profesyonel formatta döner.
     *
     * @return biçimlendirilmiş ürün bilgisi
     */
    @Override
    public String toString() {
        return String.format(
                "[%s] %s | Fiyat: %.2f₺ | Stok: %d | Min Stok: %d",
                id, name, price, stock, minStockLevel
        );
    }
}
