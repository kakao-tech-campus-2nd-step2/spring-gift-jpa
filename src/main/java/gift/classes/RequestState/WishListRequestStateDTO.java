package gift.classes.RequestState;

import gift.dto.WishDto;
import java.util.List;

public class WishListRequestStateDTO extends RequestStateDTO {
    private final List<WishDto> wishes;
    public WishListRequestStateDTO(RequestStatus requestStatus, String details, List<WishDto> wishes) {
        super(requestStatus, details);
        this.wishes = wishes;
    }

    public List<WishDto> getWishes() {
        return wishes;
    }
}
