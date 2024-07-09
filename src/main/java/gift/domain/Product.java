package gift.domain;


import gift.dto.request.ProductRequest;

public class Product {
    private long id;
    private String name;
    private long price;
    private String imageUrl;

    public Product(){}

    public Product(long id, String name, long price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }


    public Product(String name, long price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public long getId(){
        return id;
    }
    public void setId(long id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public long getPrice(){
        return price;
    }
    public void setPrice(long price){
        this.price = price;
    }
    public String getImageUrl(){
        return imageUrl;
    }
    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public static Product RequestToEntity(ProductRequest productRequest){
        return new Product(productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl());
    }
}
