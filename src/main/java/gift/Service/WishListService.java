package gift.Service;

import gift.Model.RequestWishListDTO;
import gift.Model.ResponseWishListDTO;
import gift.Model.User;
import gift.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;


    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }

    public void addWishList(User user, RequestWishListDTO requestWishListDTO) {
        wishListRepository.insertWishList(user.getEmail(), requestWishListDTO);
    }

    public List<ResponseWishListDTO> getWishList(User user) {
        return wishListRepository.selectWishList(user.getEmail());
    }

    public List<ResponseWishListDTO> editWishList(User user, RequestWishListDTO requestWishListDTO) {
        return wishListRepository.updateWishList(user.getEmail(), requestWishListDTO);
    }

    public List<ResponseWishListDTO> deleteWishList(User user, RequestWishListDTO requestWishListDTO) {
        return wishListRepository.deleteWishList(user.getEmail(), requestWishListDTO);
    }
}
