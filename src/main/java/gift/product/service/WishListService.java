package gift.product.service;

import gift.product.dao.MemberDao;
import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.model.Member;
import gift.product.model.Product;
import gift.product.model.Wish;
import gift.product.util.CertifyUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import gift.product.model.WishProduct;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class WishListService {
    private final WishListDao wishListDao;
    private final CertifyUtil certifyUtil;
    private final WishListValidation wishListValidation;
    private final MemberDao memberDao;
    private final ProductDao productDao;

    @Autowired
    public WishListService(WishListDao wishListDao, CertifyUtil certifyUtil, WishListValidation wishListValidation, MemberDao memberDao, ProductDao productDao) {
        this.wishListDao = wishListDao;
        this.certifyUtil = certifyUtil;
        this.wishListValidation = wishListValidation;
        this.memberDao = memberDao;
        this.productDao = productDao;
    }

    public Collection<WishProduct> getAllProducts(HttpServletRequest request) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        Collection<Wish> wishList = wishListDao.findAllByMember(memberDao.findMEmberByEmailLike(certifyUtil.getEmailByToken(token)));
        Collection<WishProduct> wishList2 = new ArrayList<>();
        for(Wish wish : wishList) {
            Product product = productDao.findById(wish.getProduct().getId()).orElse(null);
            if(product != null) {
                wishList2.add(new WishProduct(
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
                    )
                );
            }
        }
        return wishList2;
    }

    public ResponseEntity<String> registerWishProduct(HttpServletRequest request, Map<String, Long> requestBody) {

        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        productDao.existsById(requestBody.get("productId"));

        wishListDao.save(
                new Wish(
                        memberDao.findByEmail(certifyUtil.getEmailByToken(token)).get(),
                        productDao.findById(requestBody.get("productId")).get()
                )
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, Long id) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        wishListValidation.deleteValidation(id, memberDao.findByEmail(certifyUtil.getEmailByToken(token)).get());
        wishListDao.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }
}
