package gift.entity;

public class Product {
    private long id;
    private String name;
    private String url;
    private long price;

    public Product(long id, String name, long price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public Product(String name, long price, String url) {
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public long getId() { return id; }

    public String getName() {
        return name;
    }

    public long getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

}
