package inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Inventory implements Storable {

    private List<Product> products = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();

    public void addProduct(Product product) {
        products.add(product);
    }

    public boolean removeProduct(String productId) {
        return products.removeIf(p -> p.getId().equals(productId));
    }

    public Optional<Product> findProductById(String id) {
        return products.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void updateStock(String productId, int newStock) {
        Product p = findProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + productId));
        p.setStock(newStock);
    }

    public List<Product> searchProduct(String keyword) {
        String lower = keyword.toLowerCase();
        return products.stream()
                .filter(p -> p.getName().toLowerCase().contains(lower))
                .collect(Collectors.toList());
    }

    public List<Product> listLowStock() {
        return products.stream()
                .filter(this::checkLowStock)
                .collect(Collectors.toList());
    }

    public void addSupplier(Supplier supplier) {
        suppliers.add(supplier);
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }

    @Override
    public boolean checkLowStock(Product product) {
        return product.getStock() <= product.getMinStockLevel();
    }
}
