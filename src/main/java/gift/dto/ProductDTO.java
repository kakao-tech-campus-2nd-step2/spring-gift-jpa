package gift.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    @Size(max = 15, message = "?í’ˆ ?´ë¦„?€ ìµœë? 15?ê¹Œì§€ ?…ë ¥?????ˆìŠµ?ˆë‹¤.")
    @Pattern(
            regexp = "^[a-zA-Z0-9 ()\\[\\]+,&/_-]*$",
            message = "?í’ˆ ?´ë¦„?ëŠ” ?ë¬¸?? ?«ì, ê³µë°±, (), [], +, -, &, /, _ ë§??¬ìš©?????ˆìŠµ?ˆë‹¤."
    )
    public String name;

    @NotNull(message = "?í’ˆ ê°€ê²©ì? ?„ìˆ˜ ??ª©?…ë‹ˆ??")
    public Integer price;

    @NotEmpty(message = "?´ë?ì§€ URL?€ ?„ìˆ˜ ??ª©?…ë‹ˆ??")
    public String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name.contains("ì¹´ì¹´??)) {
            throw new IllegalArgumentException("?í’ˆ ?´ë¦„??'ì¹´ì¹´??ê°€ ?¬í•¨??ê²½ìš° ?´ë‹¹ MD?€ ?‘ì˜ê°€ ?„ìš”?©ë‹ˆ??");
        }
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

