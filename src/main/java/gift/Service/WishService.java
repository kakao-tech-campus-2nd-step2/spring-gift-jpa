package gift.Service;

import gift.Model.*;
import gift.Repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishService {
    private final WishRepository WishRepository;


    public WishService(WishRepository WishRepository) {
        this.WishRepository = WishRepository;
    }

    public void addWish(Member member, RequestWishDTO requestWishDTO) {
        Wish wish = new Wish(member.getId(), requestWishDTO.getProductId(), requestWishDTO.getCount());
        WishRepository.save(wish);
    }

    public List<ResponseWishDTO> getWish(Member member) {
        return WishRepository.findWishsByMemberId(member.getId());
    }

    @Transactional
    public List<ResponseWishDTO> editWish(Member member, RequestWishDTO requestWishDTO) {
        Wish wish = WishRepository.findByMemberIdAndProductId(member.getId(), requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다"));
        wish.setCount(requestWishDTO.getCount());
        return getWish(member);
    }

    @Transactional
    public List<ResponseWishDTO> deleteWish(Member member, RequestWishDTO requestWishDTO) {
        Wish wish = WishRepository.findByMemberIdAndProductId(member.getId(), requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다"));
        WishRepository.deleteById(wish.getId());
        return getWish(member);
    }
}