package gift.model.product;

public class Product {

    private final long id;
    private final ProductName name;
    private final int price;
    private final String imageUrl;
    private final int amount;

    public Product(Long id, ProductName name, int price, String imageUrl, int amount){
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public ProductName getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isProductEnough(int purchaseAmount){
        if(amount > purchaseAmount){
            return true;
        }
        return false;
    }
}
