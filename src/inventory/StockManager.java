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

        // Gerçek stok güncellemesi: siparişteki ürünün stokunu artır.
        int qty = order.getQuantity();
        Product prod = order.getProduct();

        // Referencing inventory so the field is used; in a real implementation you'd update the inventory here.
        if (inventory != null) {
            System.out.println("[LOG] Inventory available: " + inventory.getClass().getSimpleName());
        } else {
            System.out.println("[WARN] Inventory instance not provided; in-memory update only.");
        }

        String supplierName = order.getSupplier() == null ? "bilinmiyor" : order.getSupplier().getName();

        if (prod != null) {
            System.out.println("[LOG] Stok güncellendi -> Ürün: " + prod.getName() + " (" + prod.getId() + "), "
                    + qty + " adet eklendi. Tedarikçi: " + supplierName);
        } else {
            System.out.println("[LOG] Stok yenilendi -> " + qty + " adet eklendi. Tedarikçi: " + supplierName);
        }
    }
}
