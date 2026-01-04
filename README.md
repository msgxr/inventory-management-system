

Inventory Management System (Stok Yönetim Sistemi)

Proje Tanımı

Bu proje, Nesne Tabanlı Programlama (OOP) prensipleri esas alınarak geliştirilmiş, Java tabanlı bir Stok Yönetim Sistemi uygulamasıdır. Sistem; ürünlerin, tedarikçilerin ve stok hareketlerinin yönetilmesini amaçlamakta olup, gerçek hayattaki basit bir envanter yönetimi senaryosunu modellemektedir. Proje kapsamında ürün ekleme–silme, stok güncelleme, düşük stok analizi, sipariş oluşturma ve stok yenileme (restock) gibi temel işlevler gerçekleştirilmiştir.

Uygulama konsol tabanlı çalışmakta, verileri çalışma süresince RAM üzerinde yönetmekte ve program sonlandığında verileri CSV (Comma-Separated Values) dosyasına kaydederek kalıcılığı sağlamaktadır.



Kullanılan Teknolojiler
	•	Programlama Dili: Java
	•	Java Sürümü: JDK 8 ve üzeri
	•	Build Aracı: Gradle
	•	Test Altyapısı: JUnit
	•	Veri Saklama: CSV dosyası
	•	IDE: VS Code



Sistem Mimarisi ve OOP Yapısı

Proje, nesne tabanlı tasarım ilkelerine uygun şekilde yapılandırılmıştır:
	•	Encapsulation: Tüm alanlar private olarak tanımlanmış, erişim kontrollü getter/setter metotları ile sağlanmıştır.
	•	Inheritance: PerishableProduct sınıfı, Product sınıfından türetilerek kalıtım kullanılmıştır.
	•	Polymorphism: Ürünler ortak Product referansı üzerinden yönetilmektedir.
	•	Interface Kullanımı: Storable arayüzü ile düşük stok kontrolü soyutlanmıştır.
	•	Single Responsibility: Her sınıf tek bir sorumluluğa sahiptir.



Sınıflar ve Görevleri
	•	Inventory:
Ürün ve tedarikçi listesini yönetir. Ürün ekleme, silme, arama, stok güncelleme, düşük stok analizi ve CSV okuma/yazma işlemlerini gerçekleştirir.
	•	Product:
Ürün bilgilerini (ID, ad, fiyat, stok, minimum stok seviyesi) tutan temel sınıftır.
	•	PerishableProduct:
Son kullanma tarihi olan bozulabilir ürünleri temsil eder.
	•	Supplier:
Tedarikçi bilgilerini (ID, ad, iletişim bilgisi) tutar.
	•	Order:
Sipariş işlemlerini ve siparişe bağlı ürün–tedarikçi ilişkisini temsil eder.
	•	StockManager:
Sipariş oluşturma ve stok yenileme (restock) iş mantığını içerir.
	•	Storable (Interface):
Düşük stok kontrolü için ortak davranışı tanımlar.



Veri Kalıcılığı (CSV Kullanımı)

Sistem, ürün verilerini inventory.csv dosyasında saklamaktadır. CSV dosyası aşağıdaki formatta tutulur:

id,name,price,stock,minStock

	•	Program başlatıldığında, mevcut CSV dosyası okunur ve ürünler sisteme yüklenir.
	•	Program çalışırken tüm işlemler RAM üzerinde gerçekleştirilir.
	•	Program sonlandırıldığında, güncel ürün listesi otomatik olarak CSV dosyasına yazılır.

Bu yaklaşım sayesinde veriler kaybolmaz ve sistem bir sonraki çalıştırmada kaldığı yerden devam eder.



Programın Çalıştırılması

Gereksinimler
	•	Java JDK 8 veya üzeri
	•	Gradle (veya IDE içinden Gradle desteği)

Çalıştırma Adımları
	1.	Proje GitHub üzerinden klonlanır:

git clone https://github.com/msgxr/inventory-management-system.git


	2.	Proje dizinine girilir:

cd inventory-management-system


	3.	Gradle ile proje çalıştırılır:

gradle run



Alternatif olarak, IDE üzerinden inventory.Main sınıfı doğrudan çalıştırılabilir.



Kullanım Senaryosu
	•	Kullanıcıdan konsol üzerinden tedarikçi bilgileri alınır.
	•	Kullanıcı, sisteme ürün ekleyebilir (bozulabilir veya normal ürün).
	•	Ürünler üzerinde arama yapılabilir.
	•	Düşük stok seviyesindeki ürünler listelenir.
	•	Sipariş oluşturulur ve stok yenileme işlemi gerçekleştirilir.
	•	Program kapanırken tüm veriler otomatik olarak CSV dosyasına kaydedilir.



Testler

Proje kapsamında aşağıdaki sınıflar için JUnit testleri yazılmıştır:
	•	ProductTest
	•	InventoryTest
	•	StockManagerTest

Testler; ürün ekleme, stok artırma–azaltma, düşük stok kontrolü, sipariş oluşturma ve restock işlemleri gibi kritik fonksiyonları kapsamaktadır.
Tüm testler başarıyla geçmiştir (%100 green).



UML Diyagramları

Proje kapsamında:

-Sınıf Diyagramı
-Use Case Diyagramı

hazırlanmış ve proje dizini içerisinde PDF formatında sunulmuştur.



Sonuç

Bu proje, nesne tabanlı programlama kavramlarını gerçekçi bir senaryo üzerinden uygulamayı amaçlayan, modüler, okunabilir ve genişletilebilir bir stok yönetim sistemi sunmaktadır. CSV tabanlı veri kalıcılığı sayesinde basit ama etkili bir çözüm geliştirilmiştir.
