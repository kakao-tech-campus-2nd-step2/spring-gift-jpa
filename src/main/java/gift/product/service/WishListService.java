package gift.product.service;

import gift.product.repository.MemberRepository;
import gift.product.repository.ProductRepository;
import gift.product.repository.WishListRepository;
import gift.product.model.Wish;
import gift.product.util.JwtUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final JwtUtil jwtUtil;
    private final WishListValidation wishListValidation;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishListService(WishListRepository wishListRepository, JwtUtil jwtUtil, WishListValidation wishListValidation, MemberRepository memberRepository, ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.jwtUtil = jwtUtil;
        this.wishListValidation = wishListValidation;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public Page<Wish> getAllProducts(String authorization, Pageable pageable) {
        System.out.println("[WishListService] getAllProducts()");
        String token = jwtUtil.checkAuthorization(authorization);
        String email = jwtUtil.getEmailByToken(token);
        Long memberId = memberRepository.findByEmail(email).getId();
        return  wishListRepository.findAllByMemberId(memberId, pageable);
    }

    public ResponseEntity<String> registerWishProduct(HttpServletRequest request, Map<String, Long> requestBody) {
        System.out.println("[WishListService] registerWishProduct()");
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        productRepository.existsById(requestBody.get("productId"));

        wishListRepository.save(
                new Wish(
                        memberRepository.findByEmail(jwtUtil.getEmailByToken(token)),
                        productRepository.findById(requestBody.get("productId")).get()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, Long id) {
        System.out.println("[WishListService] deleteWishProduct()");
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        wishListValidation.deleteValidation(id, memberRepository.findByEmail(jwtUtil.getEmailByToken(token)));
        wishListRepository.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }
}