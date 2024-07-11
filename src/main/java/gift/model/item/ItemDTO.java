package gift.model.item;

import java.util.Objects;

public class ItemDTO {

    private final Long id;
    private final String name;
    private final Long price;
    private final String imgUrl;

    public ItemDTO(Long id, String name, Long price, String imgUrl) {
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
    public int hashCode() {
        return Objects.hash(id, name, price, imgUrl);
    }

    public Item toEntity() {
        return new Item(id, name, price, imgUrl);
    }

}