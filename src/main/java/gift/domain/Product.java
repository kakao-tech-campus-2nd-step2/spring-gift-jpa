package gift.domain;

public class Product {
    Long id;
    String name;
    String description;
    Integer price;
    String imageUrl;

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
}
