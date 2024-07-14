package gift.service;

import gift.model.Member;
import gift.model.Product;
import gift.model.Wish;
import gift.DTO.WishDTO;
import gift.repository.WishRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WishService {
    private final MemberService memberService;
    private final ProductService productService;
    private final WishRepository wishRepository;

    public WishService(MemberService memberService, ProductService productService, WishRepository wishRepository) {
        this.memberService = memberService;
        this.productService = productService;
        this.wishRepository = wishRepository;
    }

    public List<WishDTO> getWishlist(HttpServletRequest request, int page, String sortBy, String sortOrder) throws AuthenticationException {
        long memberId = memberService.getIdByToken(request);

        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(page, 10, sort);
        Page<Wish> pageWishlist = wishRepository.findByMember_Id(memberId, pageable);
        List<Wish> wishlist = pageWishlist.getContent();

        List<WishDTO> list = wishlist.stream()
                .map(WishDTO::getWishDTO)
                .collect(Collectors.toList());

        return list;
    }

    private Sort getSort(String sortBy, String sortOrder){
        Sort sort = Sort.by(sortBy);
        if(sortOrder.equals("desc")){
            return sort.descending();
        }
        return sort;
    }

    public void postWishlist(Long productId, HttpServletRequest request) throws AuthenticationException {
        Member member = memberService.getMemberByAuth(request);
        Product product = productService.getProductById(productId);
        Wish wish = new Wish(member, product);
        wishRepository.save(wish);
    }

    public void deleteProduct(Long id){
        wishRepository.deleteById(id);
    }
}
