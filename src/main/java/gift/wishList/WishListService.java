package gift.wishList;

import gift.product.Product;
import gift.product.ProductRepository;
import gift.user.User;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.productRepository = productRepository;
    }

    public WishListDTO addWish(WishListDTO wishListDTO, User user) {
        Product product = productRepository.findById(wishListDTO.getProductID()).orElseThrow();
        WishList wishList = new WishList(wishListDTO.getCount());
        user.addWishList(wishList);
        product.addWishList(wishList);
        wishListRepository.save(wishList);
        return new WishListDTO(wishList);
    }

    public List<WishListDTO> findByUser(User user) {
        List<WishList> wishLists = wishListRepository.findByUser(user);
        List<WishListDTO> wishListDTOS = new ArrayList<>();
        for (WishList wishList : wishLists) {
            wishListDTOS.add(new WishListDTO(wishList));
        }
        return wishListDTOS;
    }

    public WishListDTO updateCount(CountDTO count, Long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.setCount(count.getCount());
        return new WishListDTO(wishList);
    }

    public void deleteByID(Long id) {
        WishList wishList = wishListRepository.findById(id).orElseThrow();
        wishList.getUser().removeWishList(wishList);
        wishList.getProduct().removeWishList(wishList);
        wishListRepository.deleteById(id);
    }

    public Page<WishListDTO> getWishListsPages(int pageNum, int size, User user, String sortBy, String sortDirection) {
        Pageable pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.asc(sortBy)));
        if (Objects.equals(sortDirection, "desc")) {
            pageable = PageRequest.of(pageNum, size, Sort.by(Sort.Order.desc(sortBy)));
        }

        Page<WishList> wishLists = wishListRepository.findByUser(user, pageable);
        return wishLists.map(WishListDTO::new);

    }
}
