package gift.model.item;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ItemForm {

    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 15, message = "이름은 15자를 넘길 수 없습니다.")
    @Pattern(regexp = "[a-zA-Z0-9가-힣() +\\-&\\[\\]/_]*", message = "(),[],+,-,&,/,_ 를 제외한 특수문자는 사용이 불가합니다.")
    @Pattern(regexp = "^(?!.*카카오).*$", message = "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용 가능합니다.")
    private final String name;
    @NotNull(message = "가격은 필수 입력입니다.")
    @Positive(message = "음수는 입력 불가합니다.")
    private final Long price;
    private final String imgUrl;

    public ItemForm(String name, Long price, String imgUrl) {
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
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

}
