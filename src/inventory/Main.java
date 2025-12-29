package inventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        // ▶ Program başlarken CSV'den yükle
        inventory.loadFromCSV("inventory.csv");

        StockManager manager = new StockManager(inventory);
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Tedarikçi Bilgileri ---");

        System.out.print("Tedarikçi ID: ");
        String supId = sc.nextLine().trim();

        System.out.print("Tedarikçi İsmi: ");
        String supName = sc.nextLine().trim();

        System.out.print("Tedarikçi İletişim (email/telefon): ");
        String supContact = sc.nextLine().trim();

        // Boş girilirse varsayılan değerler
        String finalId = supId.isEmpty() ? "S1" : supId;
        String finalName = supName.isEmpty() ? "ABC Tedarik" : supName;
        String finalContact = supContact.isEmpty() ? "abc@tedarik.com" : supContact;

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

            if (!per.isEmpty() && (per.equalsIgnoreCase("E")
                    || per.equalsIgnoreCase("Y")
                    || per.equalsIgnoreCase("evet"))) {

                System.out.print("Son kullanma tarihi (YYYY-MM-DD): ");
                LocalDate exp;
                try {
                    exp = LocalDate.parse(sc.nextLine().trim());
                } catch (Exception e) {
                    exp = LocalDate.now().plusDays(7);
                }

                inventory.addProduct(new PerishableProduct(
                        id.isEmpty() ? "P" + (i + 1) : id,
                        name.isEmpty() ? "Ürün" + (i + 1) : name,
                        price, stock, minStock, exp
                ));

            } else {

                inventory.addProduct(new Product(
                        id.isEmpty() ? "P" + (i + 1) : id,
                        name.isEmpty() ? "Ürün" + (i + 1) : name,
                        price, stock, minStock
                ));
            }
        }

        sc.close();

        System.out.println("\n== TÜM ÜRÜNLER ==");
        for (Product p : inventory.getAllProducts()) {
            System.out.println(p);
        }

        System.out.println("\n== Arama Testi ==");
        if (!inventory.getAllProducts().isEmpty()) {
            Product first = inventory.getAllProducts().get(0);
            String term = first.getName().length() >= 2
                    ? first.getName().substring(0, 2)
                    : first.getName();

            System.out.println("Aranan terim: '" + term + "'");
            inventory.searchProduct(term).forEach(System.out::println);
        } else {
            System.out.println("Arama yapılamıyor — envanter boş.");
        }

        System.out.println("\n== Az Stoktaki Ürünler ==");
        List<Product> lowStock = inventory.listLowStock();
        if (lowStock.isEmpty()) {
            System.out.println("Düşük stokta ürün yok.");
        } else {
            lowStock.forEach(System.out::println);
        }

        System.out.println("\n== Sipariş ve Restock Testi ==");
        if (!inventory.getAllProducts().isEmpty()) {
            Product first = inventory.getAllProducts().get(0);
            Order order = manager.createOrder(first, 5, supplier);
            manager.restock(order);
            System.out.println("Sipariş işlendi: " + order);
        }

        System.out.println("\n== Restock Sonrası Ürünler ==");
        inventory.getAllProducts().forEach(System.out::println);

        // ▶ Program kapanırken CSV’ye kaydet
        inventory.saveToCSV("inventory.csv");
    }
}