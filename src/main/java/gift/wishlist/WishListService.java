package gift.wishlist;

import gift.product.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListRepository wishListRepository;
    private final ProductService productService;

    @Autowired
    public WishListService(WishListRepository wishListRepository, ProductService productService) {
        this.wishListRepository = wishListRepository;
        this.productService = productService;
    }

    public List<WishListDTO> getAllWishLists() {
        try {
            return wishListRepository.findAll().stream()
                .map(WishListDTO::fromWishList)
                .toList();
        }catch(Exception e){
            throw new RuntimeException("위시리스트 오류", e);
        }
    }

    public List<WishListDTO> getWishListsByEmail(String email) {
        try{
            return wishListRepository.findAllByEmail(email).stream()
                .map(WishListDTO::fromWishList)
                .toList();
        }catch(Exception e){
            throw new RuntimeException("위시리스트 오류", e);
        }
    }

    public void addWishList(WishListDTO wishList) {
        if(wishListRepository.existsByProductId(wishList.getProductId())){
            throw new IllegalArgumentException("위시리스트에 존재하는 상품입니다.");
        }
        wishListRepository.save(wishList.toWishList());
    }

    public void updateWishList(String email, long productId, int num) throws NotFoundException {
        WishList wishList = Optional.ofNullable(wishListRepository.findByEmail(email)).orElseThrow(
            NotFoundException::new);
        if(!existsByEmailAndProduct_id(email, productId)){
            throw new IllegalArgumentException(email+"의 위시리스트에는 "+productService.getProductById(productId).getName()+" 상품이 존재하지 않습니다.");
        }
        wishList.update(email, productId, num);
        wishListRepository.save(wishList);
    }

    public Boolean existsByEmailAndProduct_id(String email, long productId){
        return wishListRepository.existsByEmailAndProductId(email,productId);
    }

    public void deleteWishList(String email, long productId) throws NotFoundException {
        if(!existsByEmailAndProduct_id(email,productId)){
            throw new IllegalArgumentException(email+"의 위시리스트에는 "+productService.getProductById(productId).getName()+" 상품이 존재하지 않습니다.");
        }
        wishListRepository.deleteByEmailAndProductId(email, productId);
    }
}
