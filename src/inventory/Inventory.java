package inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Envanterdeki ürünleri ve tedarikçileri yöneten ana sınıftır.
 * Ürün ekleme, silme, arama, stok güncelleme ve düşük stok kontrolü gibi
 * temel işlemleri içerir.
 */
public class Inventory implements Storable {

    private List<Product> products = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();

    /**
     * Envantere yeni bir ürün ekler.
     * Ürün adı trim + küçük harfe dönüştürülerek
     * duplicate (tekrarlanan) ürün adı kontrolü yapılır.
     *
     * @param product eklenecek ürün
     */
    public void addProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Ürün nesnesi null olamaz.");
        }

        // Ürün adını normalize et (trim + lower case)
        String normalizedName = product.getName().trim().toLowerCase();

        // Aynı isimde ürün var mı kontrol et
        for (Product p : products) {
            if (p.getName().trim().toLowerCase().equals(normalizedName)) {
                throw new IllegalArgumentException("Aynı isimde ürün zaten mevcut.");
            }
        }

        products.add(product);
    }

    /**
     * Verilen ürün kimliğine göre ürünü siler.
     *
     * @param productId ürün ID
     * @return başarı durumu
     */
    public boolean removeProduct(String productId) {
        return products.removeIf(p -> p.getId().equals(productId));
    }

    /**
     * ID'ye göre ürün arar.
     *
     * @param id ürün kimliği
     * @return bulunan ürün Optional olarak
     */
    public Optional<Product> findProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Ürün stok değerini günceller.
     *
     * @param productId ürün kimliği
     * @param newStock yeni stok seviyesi
     */
    public void updateStock(String productId, int newStock) {
        Product p = findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        p.setStock(newStock);
    }

    /**
     * Anahtar kelimeye göre ürün araması yapar.
     *
     * @param keyword arama kelimesi
     * @return arama sonucuna uyan ürün listesi
     */
    public List<Product> searchProduct(String keyword) {
        String lower = keyword.toLowerCase().trim();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    /**
     * Düşük stok seviyesindeki ürünleri listeler.
     *
     * @return düşük stok ürünler listesi
     */
    public List<Product> listLowStock() {
        return products.stream()
                .filter(this::checkLowStock)
                .collect(Collectors.toList());
    }

    /**
     * Yeni tedarikçi ekler.
     *
     * @param supplier tedarikçi
     */
    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    /**
     * Tüm ürünlerin listesini kopyalayarak döner.
     *
     * @return ürün listesi
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /**
     * Bir ürünün düşük stokta olup olmadığını kontrol eder.
     *
     * @param product kontrol edilecek ürün
     * @return düşük stok durumu
     */
    @Override
    public boolean checkLowStock(Product product) {
        return product.getStock() <= product.getMinStockLevel();
    }
}
