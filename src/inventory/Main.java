package inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        // Kısa açıklama: Konsol çıktıları daha okunaklı olsun diye
        // başlıkları düzenledim ve `Product.toString()` içinde küçük
        // normalizasyon yapılarak para sembolü "TL" kullanıldı.
        // Bu değişiklikler sadece görünümü iyileştirir, iş mantığına
        // dokunulmadı.


        Inventory inventory = new Inventory();
        StockManager manager = new StockManager(inventory);

        Scanner sc = new Scanner(System.in);

        System.out.println("--- Tedarikçi Bilgileri ---");

        System.out.print("Tedarikçi ID: ");
        String supId = sc.nextLine().trim();

        System.out.print("Tedarikçi İsmi: ");
        String supName = sc.nextLine().trim();

        System.out.print("Tedarikçi İletişim (email/telefon): ");
        String supContact = sc.nextLine().trim();

        // Boş girilirse default değerler atanır
        String finalId = (supId == null || supId.isEmpty()) ? "S1" : supId;
        String finalName = (supName == null || supName.isEmpty()) ? "ABC Tedarik" : supName;
        String finalContact = (supContact == null || supContact.isEmpty()) ? "abc@tedarik.com" : supContact;
        
        Supplier supplier = new Supplier(finalId, finalName, finalContact, finalContact);

        inventory.addSupplier(supplier);

        System.out.print("Kaç ürün eklemek istersiniz? ");

        int count;
        try {
            count = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            count = 0;
        }

        for (int i = 0; i < count; i++) {

            System.out.println("\n-- Ürün " + (i + 1) + " --");

            System.out.print("Ürün ID: ");
            String id = sc.nextLine().trim();

            System.out.print("Ürün İsmi: ");
            String name = sc.nextLine().trim();

            System.out.print("Fiyat (örn: 100.0): ");
            double price;
            try {
                price = Double.parseDouble(sc.nextLine().trim());
            } catch (Exception e) {
                price = 0.0;
            }

            System.out.print("Stok Miktarı (örn: 5): ");
            int stock;
            try {
                stock = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                stock = 0;
            }

            System.out.print("Minimum Stok Seviyesi (örn: 2): ");
            int minStock;
            try {
                minStock = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                minStock = 0;
            }

            System.out.print("Bozulabilir ürün mü? (E/h): ");
            String per = sc.nextLine().trim();

            if (!per.isEmpty() &&
                (per.equalsIgnoreCase("E") ||
                 per.equalsIgnoreCase("Y") ||
                 per.equalsIgnoreCase("evet"))) {

                System.out.print("Son kullanma tarihi (YYYY-MM-DD): ");
                LocalDate exp;
                try {
                    exp = LocalDate.parse(sc.nextLine().trim());
                } catch (Exception e) {
                    exp = LocalDate.now().plusDays(7); // Varsayılan SKT
                }

                PerishableProduct pp = new PerishableProduct(
                        id.isEmpty() ? ("P" + (i + 1)) : id,
                        name.isEmpty() ? ("Ürün" + (i + 1)) : name,
                        price, stock, minStock, exp
                );

                inventory.addProduct(pp);

            } else {

                Product p = new Product(
                        id.isEmpty() ? ("P" + (i + 1)) : id,
                        name.isEmpty() ? ("Ürün" + (i + 1)) : name,
                        price, stock, minStock
                );

                inventory.addProduct(p);
            }
        }

        sc.close();

        System.out.println("\n== TÜM ÜRÜNLER ==");
        for (Product p : inventory.getAllProducts()) {
            System.out.println(p);
        }

        System.out.println("\n== Arama Testi ==");
        List<Product> searchResult;
        if (!inventory.getAllProducts().isEmpty()) {
            Product firstSearchBase = inventory.getAllProducts().get(0);
            String baseName = firstSearchBase.getName() == null ? "" : firstSearchBase.getName().trim();
            String term = baseName.length() >= 2 ? baseName.substring(0, 2) : baseName;
            System.out.println("Aranan terim: '" + term + "'");
            try {
                searchResult = inventory.searchProduct(term);
            } catch (IllegalArgumentException e) {
                searchResult = java.util.Collections.emptyList();
            }
        } else {
            System.out.println("Arama yapılamıyor — envanter boş.");
            searchResult = java.util.Collections.emptyList();
        }

        if (searchResult.isEmpty()) {
            System.out.println("Arama sonucu bulunamadı.");
        } else {
            for (Product p : searchResult) {
                System.out.println(p);
            }
        }

        System.out.println("\n== Az Stoktaki Ürünler ==");
        List<Product> lowStock = inventory.listLowStock();
        if (lowStock.isEmpty()) {
            System.out.println("Düşük stokta ürün yok.");
        } else {
            for (Product p : lowStock) {
                System.out.println(p);
            }
        }

        System.out.println("\n== Sipariş ve Restock Testi ==");
        List<Product> allProducts = inventory.getAllProducts();

        if (!allProducts.isEmpty()) {
            Product first = allProducts.get(0);
            Order order = manager.createOrder(first, 5, supplier);
            manager.restock(order);
            System.out.println("Sipariş işlendi: " + order);
        } else {
            System.out.println("Stokta ürün olmadığından sipariş oluşturulamadı.");
        }

        System.out.println("\n== Restock Sonrası Ürünler ==");
        for (Product p : inventory.getAllProducts()) {
            System.out.println(p);
        }
    }
}
