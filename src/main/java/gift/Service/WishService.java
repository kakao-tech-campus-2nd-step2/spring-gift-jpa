package gift.Service;

import gift.Model.*;
import gift.Repository.ProductRepository;
import gift.Repository.WishRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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



    @Transactional
    public void addWish(Member member, RequestWishDTO requestWishDTO) {
        Product product = productRepository.findById(requestWishDTO.getProductId())
                .orElseThrow(()->new NoSuchElementException("매칭되는 물건이 없습니다."));
        Wish wish = new Wish(member, product, requestWishDTO.getCount());
        wishRepository.save(wish);
    }

    public Page<Wish> getWishList(Member member, int page, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Page<Wish> wishListPage= wishRepository.findByMember(member,pageable);
        return wishListPage;
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

    public Wish findWishByMemberAndProduct(Member member, Product product){
        Optional<Wish> wish= wishRepository.findByMemberAndProduct(member, product);
        return wish.orElseThrow(()->new NoSuchElementException("매칭되는 wish가 없습니다"));
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