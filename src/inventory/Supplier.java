package inventory;

/**
 * Tedarikçi bilgilerini temsil eden sınıftır.
 * Ürün siparişlerinde kullanılan tedarikçi adı ve iletişim bilgilerini içerir.
 */
public class Supplier {

    private String id;
    private String name;
    private String contactInfo;

    /**
     * Yeni bir Supplier nesnesi oluşturur.
     *
     * @param id tedarikçinin benzersiz kimliği
     * @param name tedarikçi adı
     * @param contactInfo iletişim bilgisi (telefon, e-posta vb.)
     */
    public Supplier(String id, String name, String contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    /** @return tedarikçi ID */
    public String getId() {
        return id;
    }

    /** @return tedarikçi adı */
    public String getName() {
        return name;
    }

    /** @return iletişim bilgisi */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * Tedarikçi bilgisini profesyonel formatta döndürür.
     */
    @Override
    public String toString() {
        return String.format(
                "Supplier[%s] %s | İletişim: %s",
                id, name, contactInfo
        );
    }
}
