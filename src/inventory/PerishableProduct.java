package inventory;

import java.time.LocalDate;

public class PerishableProduct extends Product {

    private LocalDate expirationDate;

    public PerishableProduct(String id,
                             String name,
                             double price,
                             int stock,
                             int minStockLevel,
                             LocalDate expirationDate) {
        super(id, name, price, stock, minStockLevel);
        this.expirationDate = expirationDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isExpired() {
        return expirationDate.isBefore(LocalDate.now());
    }

    @Override
    public String toString() {
        return super.toString() + " | exp=" + expirationDate;
    }
}
