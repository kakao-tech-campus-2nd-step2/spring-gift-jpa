package gift.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductUpdateRequestDTO {

    private long id;
    @NotNull(message = "비울 수 없습니다")
    @Size(min = 0, max = 15, message = "길이는 최대 15자 입니다")
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9()\\[\\]+\\-&/_]*$", message = "허용되지 않는 특수 문자가 포함되어 있습니다.")
    private String name;

    @NotNull(message = "비울 수 없스니다")
    @Min(value = 0, message = "0 이상이여야 합니다")
    private int price;

    @NotNull(message = "비울 수 없습니다")
    private String imageUrl;

    public long getId() {
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

    public void setId(long id) {
        this.id = id;
    }

    public void setName(
        @NotNull(message = "비울 수 없습니다") @Size(min = 0, max = 15, message = "길이는 최대 15자 입니다") @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-zA-Z0-9()\\[\\]+\\-&/_]*$", message = "허용되지 않는 특수 문자가 포함되어 있습니다.") String name) {
        this.name = name;
    }

    public void setPrice(
        @NotNull(message = "비울 수 없스니다") @Min(value = 0, message = "0 이상이여야 합니다") int price) {
        this.price = price;
    }

    public void setImageUrl(@NotNull(message = "비울 수 없습니다") String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
