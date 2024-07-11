package gift.product.service;

import gift.product.dao.MemberDao;
import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.util.JwtUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListDao wishListDao;
    private final JwtUtil jwtUtil;
    private final WishListValidation wishListValidation;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    @Autowired
    public WishListService(WishListDao wishListDao, JwtUtil jwtUtil, WishListValidation wishListValidation, MemberDao memberDao, ProductDao productDao) {
        this.wishListDao = wishListDao;
        this.jwtUtil = jwtUtil;
        this.wishListValidation = wishListValidation;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Collection<Product> getAllProducts(HttpServletRequest request) {
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));

        Collection<Wish> findList = wishListDao.findAllByMember(memberDao.findByEmail(jwtUtil.getEmailByToken(token)));

        List<Product> responseList = new ArrayList<>();
        for(Wish wish : findList)
            productDao.findById(wish.getProduct().getId()).ifPresent(responseList::add);

        return responseList;
    }

    public ResponseEntity<String> registerWishProduct(HttpServletRequest request, Map<String, Long> requestBody) {

        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        productDao.existsById(requestBody.get("productId"));

        wishListDao.save(
                new Wish(
                        memberDao.findByEmail(jwtUtil.getEmailByToken(token)),
                        productDao.findById(requestBody.get("productId")).get()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, Long id) {
        String token = jwtUtil.checkAuthorization(request.getHeader("Authorization"));
        wishListValidation.deleteValidation(id, memberDao.findByEmail(jwtUtil.getEmailByToken(token)));
        wishListDao.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }
}
