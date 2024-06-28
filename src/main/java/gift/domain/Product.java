package gift.domain;

public class Product {
    Long id;
    String name;
    String description;
    Integer price;
    String imageUrl;

    public Product(long id, String name, String description, int price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public void setId(long andIncrement) {
        this.id = andIncrement;
    }

    public void setName(Object name) {
        this.name = name.toString();
    }

    public void setPrice(Object price) {
        this.price = (Integer) price;
    }

    public void setDescription(Object description) {
        this.description = description.toString();
    }

    public void setImageUrl(Object imageUrl) {
        this.imageUrl = imageUrl.toString();
    }

    public Long getId() {
        return id;
    }

    public Object getName() {
        return name;
    }
    public Object getPrice() {
        return price;
    }
    public Object getDescription() {
        return description;
    }
    public String getImageUrl() {
        return imageUrl;
    }
}
