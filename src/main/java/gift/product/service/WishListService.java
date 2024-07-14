package gift.product.service;

import gift.product.dto.ProductDTO;
import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishListRepository;
import gift.product.model.Wish;
import gift.product.util.JwtUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final JwtUtil jwtUtil;
    private final WishListValidation wishListValidation;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishListService(
        WishListRepository wishListRepository,
        JwtUtil jwtUtil,
        WishListValidation wishListValidation,
        MemberRepository memberRepository,
        ProductRepository productRepository
    ) {
        this.wishListRepository = wishListRepository;
        this.jwtUtil = jwtUtil;
        this.wishListValidation = wishListValidation;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Page<ProductDTO> getAllProducts(String authorization, Pageable pageable) {
        System.out.println("[WishListService] getAllProducts()");
        String token = jwtUtil.checkAuthorization(authorization);
        String email = jwtUtil.getEmailByToken(token);
        Long memberId = memberRepository.findByEmail(email).getId();
        return  convertWishToProductDTOList(wishListRepository.findAllByMemberId(memberId, pageable), pageable);
    }

    public void registerWishProduct(HttpServletRequest request, Map<String, Long> requestBody) {
        System.out.println("[WishListService] registerWishProduct()");
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        productRepository.existsById(requestBody.get("productId"));

        wishListRepository.save(
                new Wish(
                        memberRepository.findByEmail(jwtUtil.getEmailByToken(token)),
                        productRepository.findById(requestBody.get("productId")).get()
                )
        );
    }

    public void deleteWishProduct(HttpServletRequest request, Long id) {
        System.out.println("[WishListService] deleteWishProduct()");
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        Member member = memberRepository.findByEmail(jwtUtil.getEmailByToken(token));
        wishListValidation.deleteValidation(id, member);
        wishListRepository.deleteById(id);
    }

    public Page<ProductDTO> convertWishToProductDTOList(Page<Wish> wishList, Pageable pageable) {
        List<ProductDTO> productDTOs = wishList.stream()
            .map(this::convertWishToProductDTO)
            .collect(Collectors.toList());

        return new PageImpl<>(
            productDTOs,
            pageable,
            wishList.getTotalElements()
        );
    }

    public ProductDTO convertWishToProductDTO(Wish wish) {
        Product product = wish.getProduct();
        return new ProductDTO(
            product.getName(),
            product.getPrice(),
            product.getImageUrl()
        );
    }

}
