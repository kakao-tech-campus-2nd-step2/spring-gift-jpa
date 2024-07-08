package gift.domain;


import gift.annotation.ProductName;


public class Product {


    private Long id;
    @ProductName(message = "이름 규칙을 준수해야 합니다.")
    private String name;
    private int price;
    private String imageUrl;


    public Product() {
    }

    public Product(Long id, String name, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

}
