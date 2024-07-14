package gift.Service;

import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class WishService {
    private final WishRepository wishRepository;
    private final ProductRepository productRepository;


    public WishService(WishRepository wishRepository, ProductRepository productRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
    }



    public void addWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(()->new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = new Wish(member, product, requestWishDTO.getCount());
        wishRepository.save(wish);
    }

    public List<ResponseWishDTO> getWish(Member member) {
        List<Wish> wishList = wishRepository.findWishListByMember(member);
        ListIterator<Wish> iterator = wishList.listIterator();
        List<ResponseWishDTO> responseWishDTOList = new ArrayList<>();
        while(iterator.hasNext()){
            Wish wish = iterator.next();
            responseWishDTOList.add(new ResponseWishDTO(wish.getProduct().getName(), wish.getCount()));
        }

        return responseWishDTOList;
    }

    @Transactional
    public List<ResponseWishDTO> editWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new NoSuchElementException("매칭되는 wish가 없습니다"));
        wish.setCount(requestWishDTO.getCount());
        return getWish(member);
    }

    @Transactional
    public List<ResponseWishDTO> deleteWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(() -> new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = wishRepository.findByMemberAndProduct(member, product)
                .orElseThrow(() -> new NoSuchElementException("매칭되는 wish가 없습니다"));
        wishRepository.deleteById(wish.getId());
        return getWish(member);
    }
}