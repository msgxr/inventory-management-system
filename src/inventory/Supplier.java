package inventory;

/**
 * Tedarikçi bilgilerini temsil eden sınıftır.
 * Ad, iletişim numarası, e-posta ve genel iletişim bilgilerini kapsar.
 */
public class Supplier {

    private String id;
    private String name;
    private String phone;
    private String email;

    /**
     * Yeni bir Supplier nesnesi oluşturur.
     *
     * @param id benzersiz tedarikçi kimliği
     * @param name tedarikçi adı
     * @param phone telefon numarası
     * @param email e-posta adresi
     */
    public Supplier(String id, String name, String phone, String email) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    /** @return tedarikçi ID */
    public String getId() {
        return id;
    }

    /** @return tedarikçi adı */
    public String getName() {
        return name;
    }

    /** @return telefon numarası */
    public String getPhone() {
        return phone;
    }

    /** @return email adresi */
    public String getEmail() {
        return email;
    }

    /**
     * Tedarikçi bilgilerini profesyonel formatta döndürür.
     */
    @Override
    public String toString() {
        return String.format(
                "Supplier[%s] %s | Tel: %s | Email: %s",
                id, name, phone, email
        );
    }
}
