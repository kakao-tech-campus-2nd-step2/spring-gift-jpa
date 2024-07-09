package gift.dto.request;
import jakarta.validation.constraints.*;

public class WishlistIdRequest {

    @NotNull(message = "Item ID를 입력하세요")
    private Long itemId;

    @NotNull(message = "Member ID를 입력하세요")
    private Long memberId;

    public WishlistIdRequest(Long itemId, Long memberId) {
        this.itemId = itemId;
        this.memberId = memberId;
    }

    public Long getItemId() {
        return itemId;
    }


    public Long getMemberId() {
        return memberId;
    }

}
