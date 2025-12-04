
package inventory;

/**
 * Bir ürün için verilen siparişi temsil eden sınıftır.
 * Sipariş miktarı, ilgili tedarikçi ve siparişin işlenip işlenmediği
 * bilgilerini içerir.
 */
public class Order {

    private Product product;
    private int quantity;
    private Supplier supplier;
    private boolean processed = false;

    /**
     * Yeni bir sipariş oluşturur.
     *
     * @param product sipariş verilen ürün
     * @param quantity miktar
     * @param supplier tedarikçi
     */
    public Order(Product product, int quantity, Supplier supplier) {

        if (product == null) {
            throw new IllegalArgumentException("Sipariş verilen ürün null olamaz.");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Sipariş miktarı pozitif olmalıdır.");
        }

        this.product = product;
        this.quantity = quantity;
        this.supplier = supplier;
    }

    /**
     * Siparişi işler. Sipariş işlendiğinde ürün stok miktarı artırılır.
     * Eğer sipariş daha önce işlendiyse tekrar işlenemez.
     */
    public void processOrder() {

        if (processed) {
            throw new IllegalStateException("Sipariş daha önce işlenmiş.");
        }

        product.increaseStock(quantity);
        processed = true;
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
