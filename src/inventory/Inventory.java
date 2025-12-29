package inventory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Envanterdeki ürünleri ve tedarikçileri yöneten ana sınıftır.
 * Ürünler RAM üzerinde yönetilir, aynı zamanda CSV dosyasına
 * yazılarak kalıcı hale getirilir.
 */
public class Inventory implements Storable {

    private List<Product> products = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();

    /* =========================================================
       ÜRÜN YÖNETİMİ
       ========================================================= */

    /**
     * Envantere yeni bir ürün ekler.
     * Ürün adı trim + küçük harfe dönüştürülerek
     * duplicate kontrolü yapılır.
     */
    public void addProduct(Product product) {

        if (product == null) {
            throw new IllegalArgumentException("Ürün nesnesi null olamaz.");
        }

        String normalizedName = normalize(product.getName());

        for (Product p : products) {
            if (normalize(p.getName()).equals(normalizedName)) {
                throw new IllegalArgumentException("Aynı isimde ürün zaten mevcut.");
            }
        }

        products.add(product);
    }

    /**
     * Ürün siler.
     */
    public boolean removeProduct(String productId) {
        return products.removeIf(p -> p.getId().equals(productId));
    }

    /**
     * ID’ye göre ürün bulur.
     */
    public Optional<Product> findProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    /**
     * Ürün stok güncellemesi yapar.
     */
    public void updateStock(String productId, int newStock) {
        Product p = findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı: " + productId));
        p.setStock(newStock);
    }

    /**
     * Gelişmiş ürün arama.
     */
    public List<Product> searchProduct(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            throw new IllegalArgumentException("Arama kelimesi boş olamaz.");
        }

        String searchKey = normalize(keyword);

        return products.stream()
                .filter(p -> normalize(p.getName()).contains(searchKey))
                .collect(Collectors.toList());
    }

    /**
     * Düşük stoklu ürünleri listeler.
     */
    public List<Product> listLowStock() {
        return products.stream()
                .filter(this::checkLowStock)
                .collect(Collectors.toList());
    }

    /**
     * Tüm ürünleri döndürür (kopya).
     */
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    /* =========================================================
       TEDARİKÇİ
       ========================================================= */

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public List<Supplier> getSuppliers() {
        return new ArrayList<>(suppliers);
    }

    /* =========================================================
       CSV KALICI KAYIT
       ========================================================= */

    /**
     * Ürünleri CSV dosyasına yazar.
     * Format: id,name,price,stock,minStock
     */
    public void saveToCSV(String fileName) {

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {

            writer.write("id,name,price,stock,minStock");
            writer.newLine();

            for (Product p : products) {
                writer.write(
                        p.getId() + "," +
                        p.getName() + "," +
                        p.getPrice() + "," +
                        p.getStock() + "," +
                        p.getMinStockLevel()
                );
                writer.newLine();
            }

        } catch (IOException e) {
            System.out.println("CSV yazma hatası: " + e.getMessage());
        }
    }

    /**
     * Ürünleri CSV dosyasından okur.
     */
    public void loadFromCSV(String fileName) {

        products.clear();

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {

            String line = reader.readLine(); // header atla

            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");

                Product product = new Product(
                        data[0],
                        data[1],
                        Double.parseDouble(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4])
                );

                products.add(product);
            }

        } catch (IOException e) {
            System.out.println("CSV okuma hatası: " + e.getMessage());
        }
    }

    /* =========================================================
       YARDIMCI
       ========================================================= */

    private String normalize(String text) {
        return text.trim()
                .toLowerCase()
                .replace("ı", "i")
                .replace("İ", "i");
    }

    @Override
    public boolean checkLowStock(Product product) {
        return product.getStock() <= product.getMinStockLevel();
    }
}