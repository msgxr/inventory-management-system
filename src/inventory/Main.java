package inventory;

import inventory.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();
        StockManager manager = new StockManager(inventory);

        Scanner sc = new Scanner(System.in);

        System.out.println("--- Tedarikçi bilgileri ---");

        System.out.print("Tedarikçi ID: ");

        String supId = sc.nextLine().trim();

        System.out.print("Tedarikçi isim: ");

        String supName = sc.nextLine().trim();

        System.out.print("Tedarikçi iletişim (email/telefon): ");

        String supContact = sc.nextLine().trim();

        Supplier supplier = new Supplier(supId.isEmpty() ? "S1" : supId,

                supName.isEmpty() ? "ABC Tedarik" : supName,

                supContact.isEmpty() ? "abc@tedarik.com" : supContact);

        inventory.addSupplier(supplier);

        System.out.print("Kaç ürün eklemek istersiniz? ");
        int count = 0;
        try {
            count = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            count = 0;
        }

        for (int i = 0; i < count; i++) {

            System.out.println("\n-- Ürün " + (i + 1) + " --");

            System.out.print("Ürün ID: ");

            String id = sc.nextLine().trim();

            System.out.print("Ürün isim: ");

            String name = sc.nextLine().trim();

            System.out.print("Fiyat (ör: 100.0): ");

            double price = 0.0;

            try { price = Double.parseDouble(sc.nextLine().trim()); } catch (Exception e) { price = 0.0; }

            System.out.print("Stok miktarı (ör: 5): ");

            int stock = 0;
            try { stock = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { stock = 0; }

            System.out.print("Minimum stok seviyesi (ör: 2): ");

            int minStock = 0;

            try { minStock = Integer.parseInt(sc.nextLine().trim()); } catch (Exception e) { minStock = 0; }

            System.out.print("Bozulabilir ürün mü? (E/h): ");

            String per = sc.nextLine().trim();

            if (!per.isEmpty() && (per.equalsIgnoreCase("E") || per.equalsIgnoreCase("Y") || per.equalsIgnoreCase("evet"))) {

                System.out.print("Son kullanma tarihi (YYYY-MM-DD): ");

                LocalDate exp = null;
                
                try { exp = LocalDate.parse(sc.nextLine().trim()); } catch (Exception e) { exp = LocalDate.now().plusDays(7); }
                PerishableProduct pp = new PerishableProduct(
                        id.isEmpty() ? ("P" + (i+1)) : id,
                        name.isEmpty() ? ("Ürün" + (i+1)) : name,
                        price, stock, minStock, exp);
                inventory.addProduct(pp);
            } else {
                Product p = new Product(
                        id.isEmpty() ? ("P" + (i+1)) : id,
                        name.isEmpty() ? ("Ürün" + (i+1)) : name,
                        price, stock, minStock);
                inventory.addProduct(p);
            }
        }

        sc.close();

        System.out.println("== TÜM ÜRÜNLER ==");
        for (Product p : inventory.getAllProducts()) {
            System.out.println(p);
        }

        System.out.println("\n== 'Sa' ARAMA ==");
        List<Product> searchResult = inventory.searchProduct("Sa");
        for (Product p : searchResult) {
            System.out.println(p);
        }

        System.out.println("\n== AZ STOKTAKİ ÜRÜNLER ==");
        for (Product p : inventory.listLowStock()) {
            System.out.println(p);
        }

        System.out.println("\n== SİPARİŞ VE RESTOCK ==");
        List<Product> allProducts = inventory.getAllProducts();
        if (!allProducts.isEmpty()) {
            Product firstProduct = allProducts.get(0);
            Order order = manager.createOrder(firstProduct, 5, supplier);
            manager.restock(order);
            System.out.println("Sipariş işlendi: " + order);
        } else {
            System.out.println("Sipariş atlanıyor: stokta ürün yok.");
        }
        System.out.println("\n== RESTOCK SONRASI ÜRÜNLER ==");
        for (Product p : inventory.getAllProducts()) {
            System.out.println(p);
        }
    }
}
