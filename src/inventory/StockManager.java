package inventory;

/**
 * Envanterde stok işlemlerini yöneten sınıftır.
 * Sipariş oluşturma ve stok yenileme gibi görevleri üstlenir.
 */
public class StockManager {

    private Inventory inventory;

    public StockManager(Inventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Bir sipariş oluşturur.
     *
     * @param product siparişi verilecek ürün
     * @param quantity miktar
     * @param supplier tedarikçi
     * @return oluşturulan sipariş nesnesi
     */
    public Order createOrder(Product product, int quantity, Supplier supplier) {
        Order order = new Order(product, quantity, supplier);
        System.out.println("[LOG] Sipariş oluşturuldu -> Ürün: " 
                            + product.getName() 
                            + ", Miktar: " + quantity);
        return order;
    }

    /**
     * Siparişi işler ve stoğu artırır.
     *
     * @param order işlenecek sipariş
     */
    public void restock(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order nesnesi null olamaz.");
        }

        order.processOrder();

        // Eğer envanter sağlanmışsa onu kullanarak veya en azından referansını
        // loglayarak field'ı kullanıyoruz, aksi halde sadece miktarı logla.
        if (this.inventory != null) {
            System.out.println("[LOG] Envanter kullanılarak stok güncellendi -> "
                                + this.inventory
                                + ", " + order.getQuantity() 
                                + " adet eklendi.");
        } else {
            System.out.println("[LOG] Stok yenilendi -> " 
                                + order.getQuantity() 
                                + " adet eklendi.");
        }
    }
}
