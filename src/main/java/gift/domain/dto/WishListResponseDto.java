package gift.domain.dto;

import gift.global.response.BasicResponse;
import java.util.List;
import org.springframework.http.HttpStatusCode;

public class WishListResponseDto extends BasicResponse {

    private final List<WishResponseDto> wishlist;

    public WishListResponseDto(HttpStatusCode statusCode,
        List<WishResponseDto> wishlist) {
        super(statusCode);
        this.wishlist = wishlist;
    }

    public List<WishResponseDto> getWishlist() {
        return wishlist;
    }
}
