package gift.service;

import gift.database.JpaWishRepository;
import gift.dto.WishListDTO;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

    //TODO : 연관관계 작성 이후 구현 예정
    private JpaWishRepository jdbcWishListRepository;

    public WishListServiceImpl(JpaWishRepository jdbcWishListRepository) {
        this.jdbcWishListRepository = jdbcWishListRepository;
    }

    @Override
    public void addProduct(long memberId, long productId) {

        //WishList wishList = new WishList(null, memberId, new HashMap<>());
        //wishList.updateProduct(productId, 1);
        //jdbcWishListRepository.insertWishList(wishList);
    }

    @Override
    public void deleteProduct(long memberId, long productId) {

        //jdbcWishListRepository.deleteWishList(memberId, productId);

    }

    @Override
    public void updateProduct(long memberId, long productId, int productValue) {
        //WishList wishList = jdbcWishListRepository.findByMemeberId(memberId);
        //wishList.updateProduct(productId, productValue);
        //jdbcWishListRepository.updateWishList(wishList);

    }

    @Override
    public List<WishListDTO> getWishList(long memberId) {
        //WishList wishList = jdbcWishListRepository.findByMemeberId(memberId);
        //var products = wishList.getWishList();
        //return products.keySet().stream()
        //    .map(key -> new WishListDTO(wishList.getMemberId(), key, products.get(key))).toList();
        return null;

    }
}
