package gift.service;

import gift.dto.WishListDTO;
import java.util.List;

public interface WishListService {

    void addProduct(long memberId, long productId);

    void deleteProduct(long memberId, long productId);

    void updateProduct(long memberId, long productId, int productValue);

    List<WishListDTO> getWishList(long memberId);

}
