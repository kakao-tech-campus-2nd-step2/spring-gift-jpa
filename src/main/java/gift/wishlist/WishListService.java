package gift.wishlist;

import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishListService {

    private final WishListDao wishListDao;

    @Autowired
    public WishListService(WishListDao wishListDao) {
        this.wishListDao = wishListDao;
    }
    private static final Logger logger = LoggerFactory.getLogger(WishListService.class);

    public List<WishListDTO> getAllWishLists() {
        try {
            return wishListDao.findAll().stream()
                .map(WishListDTO::fromWishList)
                .collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("위시리스트 오류", e);
        }
    }

    public List<WishListDTO> getWishListsByEmail(String email) {
        try{
            return wishListDao.selectWishListsByEmail(email).stream()
                .map(WishListDTO::fromWishList)
                .collect(Collectors.toList());
        }catch(Exception e){
            throw new RuntimeException("위시리스트 오류", e);
        }
    }

    public void addWishList(WishListDTO wishList) {
        wishListDao.insertWishList(wishList.toWishList());
    }

    public void updateWishList(String email, String name, int num) {
        wishListDao.updateWishList(email, name, num);
    }

    public WishList getWishListByEmailAndName(String email, String name){
        return wishListDao.selectWishListByEmailAndName(email,name);
    }

    public boolean deleteWishList(String email, String name) {
        if(getWishListByEmailAndName(email,name)==null){
            return false;
        }
        wishListDao.deleteWishList(email, name);
        return true;
    }
}
