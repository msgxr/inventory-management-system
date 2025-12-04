package inventory;

/**
 * Envanterde düşük stok kontrolü yapmak için kullanılan
 * arayüz tanımıdır. Bu arayüzü uygulayan sınıflar,
 * stok seviyesinin minimum seviyenin altında olup olmadığını
 * kendi mantıklarıyla kontrol etmek zorundadır.
 */
public interface Storable {

    /**
     * Ürünün minimum stok seviyesinin altına düşüp düşmediğini kontrol eder.
     *
     * @param product kontrol edilecek ürün
     * @return stok düşükse true, değilse false
     */
    boolean checkLowStock(Product product);
}
