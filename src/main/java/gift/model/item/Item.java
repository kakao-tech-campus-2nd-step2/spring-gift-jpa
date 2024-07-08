package gift.model.item;

import java.util.Objects;

public class Item {

    private final Long id;
    private final String name;
    private final Long price;
    private final String imgUrl;

    public Item(Long id, String name, Long price, String imgUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) && Objects.equals(name, item.name) && Objects.equals(
            price, item.price) && Objects.equals(imgUrl, item.imgUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, imgUrl);
    }
}
