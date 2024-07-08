package gift.domain;

public class Product {

    private Long id;
    private String name;
    private double price;


    private String imageUrl;


    public Product(Long id, String name, double price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public String getName() {
        return name;
    }


    public double getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }


}
