package gift.Service;

import gift.Model.*;
import gift.Repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;


    public WishListService(WishListRepository wishListRepository){
        this.wishListRepository = wishListRepository;
    }

    public void addWishList(Member member, RequestWishListDTO requestWishListDTO) {
        WishList wishList = new WishList(member.getEmail(), requestWishListDTO.getProductId(), requestWishListDTO.getCount());
        wishListRepository.save(wishList);
    }

    public List<ResponseWishListDTO> getWishList(Member member) {
        return wishListRepository.findWishListsByEmail(member.getEmail());
    }

    @Transactional
    public List<ResponseWishListDTO> editWishList(Member member, RequestWishListDTO requestWishListDTO) {
        WishList wishList = wishListRepository.findByEmailAndProductId(member.getEmail(), requestWishListDTO.getProductId())
                .orElseThrow(()-> new NoSuchElementException("매칭되는 물건이 없습니다"));
        wishList.setCount(requestWishListDTO.getCount());
        return getWishList(member);
    }

    @Transactional
    public List<ResponseWishListDTO> deleteWishList(Member member, RequestWishListDTO requestWishListDTO) {
        WishList wishList = wishListRepository.findByEmailAndProductId(member.getEmail(), requestWishListDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다"));
        wishListRepository.deleteById(wishList.getId());
        return getWishList(member);
    }
}
