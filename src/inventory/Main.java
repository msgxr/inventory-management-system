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

        int choice;

        Supplier supplier = null;

        do {
            System.out.println();
            System.out.println("╔══════════════════════════════════════╗");
            System.out.println("║        ENVANTER YÖNETİM SİSTEMİ       ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 1 │ Tedarikçi Ekle                    ║");
            System.out.println("║ 2 │ Ürün Ekle                         ║");
            System.out.println("║ 3 │ Ürünleri Listele                  ║");
            System.out.println("║ 4 │ Ürün Ara                          ║");
            System.out.println("║ 5 │ Kritik Stok Kontrolü              ║");
            System.out.println("║ 6 │ Sipariş Oluştur & Restock         ║");
            System.out.println("╠══════════════════════════════════════╣");
            System.out.println("║ 0 │ Çıkış ve Kaydet                   ║");
            System.out.println("╚══════════════════════════════════════╝");
            System.out.print("Seçiminiz: ");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    System.out.println("--- Tedarikçi Bilgileri ---");

                    System.out.print("Tedarikçi ID: ");
                    String supId = sc.nextLine().trim();

                    System.out.print("Tedarikçi İsmi: ");
                    String supName = sc.nextLine().trim();

                    System.out.print("Tedarikçi İletişim: ");
                    String supContact = sc.nextLine().trim();

                    String finalId = supId.isEmpty() ? "S1" : supId;
                    String finalName = supName.isEmpty() ? "ABC Tedarik" : supName;
                    String finalContact = supContact.isEmpty() ? "abc@tedarik.com" : supContact;

                    supplier = new Supplier(finalId, finalName, finalContact, finalContact);
                    inventory.addSupplier(supplier);

                    System.out.println("Tedarikçi eklendi.");
                    break;

                case 2:
                    System.out.print("Ürün ID: ");
                    String id = sc.nextLine().trim();

                    System.out.print("Ürün İsmi: ");
                    String name = sc.nextLine().trim();

                    System.out.print("Fiyat: ");
                    double price = Double.parseDouble(sc.nextLine().trim());

                    System.out.print("Stok: ");
                    int stock = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Minimum Stok: ");
                    int minStock = Integer.parseInt(sc.nextLine().trim());

                    System.out.print("Bozulabilir mi? (E/h): ");
                    String per = sc.nextLine().trim();

                    if (per.equalsIgnoreCase("E")) {
                        System.out.print("Son kullanma tarihi (YYYY-MM-DD): ");
                        LocalDate exp = LocalDate.parse(sc.nextLine().trim());

                        inventory.addProduct(new PerishableProduct(
                                id, name, price, stock, minStock, exp
                        ));
                    } else {
                        inventory.addProduct(new Product(
                                id, name, price, stock, minStock
                        ));
                    }

                    System.out.println("Ürün eklendi.");
                    break;

                case 3:
                    System.out.println("== TÜM ÜRÜNLER ==");
                    inventory.getAllProducts().forEach(System.out::println);
                    break;

                case 4:
                    System.out.print("Arama kelimesi: ");
                    String term = sc.nextLine();
                    inventory.searchProduct(term).forEach(System.out::println);
                    break;

                case 5:
                    System.out.println("== KRİTİK STOKTAKİ ÜRÜNLER ==");
                    inventory.listLowStock().forEach(System.out::println);
                    break;

                case 6:
                    if (supplier == null || inventory.getAllProducts().isEmpty()) {
                        System.out.println("Sipariş için tedarikçi veya ürün yok.");
                        break;
                    }

                    Product first = inventory.getAllProducts().get(0);
                    Order order = manager.createOrder(first, 5, supplier);
                    manager.restock(order);
                    System.out.println("Sipariş işlendi: " + order);
                    break;

                case 0:
                    inventory.saveToCSV("inventory.csv");
                    System.out.println("Veriler CSV dosyasına kaydedildi. Çıkılıyor...");
                    break;

                default:
                    System.out.println("Geçersiz seçim.");
            }

        } while (choice != 0);

        sc.close();
    }
}