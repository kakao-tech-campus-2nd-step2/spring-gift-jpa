package gift.entity;

public class Product {
    private long id;
    private String name;
    private String url;
    private int price;

    public Product(int id, String name, int price, String url) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.url = url;
    }

    public long getId() { return id; }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

}
