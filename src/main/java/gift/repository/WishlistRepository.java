package gift.repository;

import gift.model.WishList;

public interface WishlistRepository {
    boolean save(WishList wishList);

    boolean delete(String email);

    WishList findByEmail(String email);
}
