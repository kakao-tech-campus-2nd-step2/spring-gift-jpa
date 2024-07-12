package gift.Service;

import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishService {
    private final WishRepository WishRepository;
    private final ProductRepository productRepository;


    public WishService(WishRepository WishRepository, ProductRepository productRepository) {
        this.WishRepository = WishRepository;
        this.productRepository = productRepository;
    }



    public void addWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(()->new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = new Wish(member, product, requestWishDTO.getCount());
        WishRepository.save(wish);
    }

    public List<ResponseWishDTO> getWish(Member member) {
        return WishRepository.findWishListByMember(member);
    }

    @Transactional
    public List<ResponseWishDTO> editWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = WishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new NoSuchElementException("매칭되는 wish가 없습니다"));
        wish.setCount(requestWishDTO.getCount());
        return getWish(member);
    }

    @Transactional
    public List<ResponseWishDTO> deleteWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = WishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new NoSuchElementException("매칭되는 wish가 없습니다"));
        WishRepository.deleteById(wish.getId());
        return getWish(member);
    }
}