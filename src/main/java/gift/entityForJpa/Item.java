package gift.entityForJpa;

import gift.dtoForJpa.ItemDTO;
import gift.exception.BadRequestExceptions.BlankContentException;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @NotBlank(message = "상품 이름을 입력해주세요.")
    @Length(min = 1, max = 15, message = "제품명 길이는 1~15자만 가능합니다.")
    @Pattern(regexp = "[a-zA-Z0-9ㄱ-ㅎ가-힣()\\[\\]+\\-&/_ ]+", message = "( ), [ ], +, -, &, /, _을 제외한 특수문자는 입력할 수 없습니다.")
    @Pattern(regexp = "^((?!카카오).)*$", message = "카카오가 포함된 문구는 담당 MD와 협의한 후에 사용해주시기 바랍니다.")
    @Column(nullable = false)
    @Size(max=15)
    private String name;

    @NotNull(message = "가격을 입력해주세요")
    @Min(value = 0, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    @Max(value = Integer.MAX_VALUE, message = "가격은 0 이상, 2147483647 이하이여야 합니다.")
    @Column(nullable = false)
    private Integer price;

    @Size(max=255)
    @Column(nullable = false)
    @NotBlank(message = "이미지 URL을 입력해주세요.")
    @URL(message = "URL 형식이 아닙니다.")
    private String imageUrl;

    public Item() {
    }

    public Item(ItemDTO itemDTO) {
        this(itemDTO.id(), itemDTO.name(), itemDTO.price(), itemDTO.imageUrl());
        blankCatch(itemDTO);
    }

    public Item(String name, Integer price, String imageUrl) {
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Item(Long id, String name, Integer price, String imageUrl) {
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

    public Integer getPrice() {
        return price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public ItemDTO convertToProductDTO(Item item) {
        return new ItemDTO(item.getId(), item.getName(), item.getPrice(), item.getImageUrl());
    }

    private void blankCatch(ItemDTO itemDTO) throws BlankContentException {
        if (itemDTO.name().isBlank() || itemDTO.price() == null || itemDTO.imageUrl()
                .isBlank()) {
            throw new BlankContentException("입력 값에 빈 곳이 있습니다. 다시 입력해주세요");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != getClass()) {
            return false;
        }
        Item item = (Item) o;
        return Objects.equals(id, item.id) &&
                Objects.equals(name, item.name) &&
                Objects.equals(price, item.price) &&
                Objects.equals(imageUrl, item.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
