package gift.service;



import gift.database.JpaMemberRepository;
import gift.database.JpaProductRepository;
import gift.database.JpaWishRepository;
import gift.dto.ProductDTO;
import gift.dto.WishListDTO;
import gift.exceptionAdvisor.MemberNoSuchException;
import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

@Service
public class WishListServiceImpl implements WishListService {

    private final JpaMemberRepository jpaMemberRepository;
    private final JpaWishRepository jpaWishRepository;
    private final JpaProductRepository jpaProductRepository;

    public WishListServiceImpl(JpaMemberRepository jpaMemberRepository,
        JpaWishRepository jpaWishRepository, JpaProductRepository jpaProductRepository) {
        this.jpaMemberRepository = jpaMemberRepository;
        this.jpaWishRepository = jpaWishRepository;
        this.jpaProductRepository = jpaProductRepository;

    }

    @Override
    public void addProduct(long memberId, long productId) {

        Member member = jpaMemberRepository.findById(memberId).orElseThrow(MemberNoSuchException::new);
        Product product = jpaProductRepository.findById(productId).orElseThrow();
        member.addProduct(product);
    }

    @Override
    public void deleteProduct(long memberId, long productId) {

        Member member = jpaMemberRepository.findById(memberId).orElseThrow(MemberNoSuchException::new);
        Product product = jpaProductRepository.findById(productId).orElseThrow();
        member.delProduct(product);

    }

    @Override
    public void updateProduct(long memberId, long productId, int productValue) {
        Wish wish = jpaWishRepository.findByMemberIdAndProductId(memberId,productId).orElseThrow();
        wish.setValue(productValue);

    }

    @Override
    public WishListDTO getWishList(long memberId) {
       Member member = jpaMemberRepository.findById(memberId).orElseThrow();
       Map<String,Integer> wishList = member.getWishList().stream().collect(Collectors.toMap(Wish::getProductName,Wish::getProductCount));
       return new WishListDTO(member.getId(),wishList);
    }

    @Override
    public WishListDTO getWishListPage(long memberId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Map<String,Integer> wishlist = jpaWishRepository.findByMemberId(memberId,pageable).stream().collect(Collectors.toMap(
             Wish::getProductName,Wish::getProductCount
        ));
        return new WishListDTO(memberId,wishlist);
    }

}
