package gift.product.service;

import gift.product.dao.MemberDao;
import gift.product.dao.ProductDao;
import gift.product.dao.WishListDao;
import gift.product.model.Product;
import gift.product.model.WishProduct;
import gift.product.util.CertifyUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import gift.product.model.WishProduct2;
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
    private final AtomicLong idCounter = new AtomicLong();
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

    public Collection<WishProduct2> getAllProducts(HttpServletRequest request) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        Collection<WishProduct> wishList = wishListDao.findAllByMemberId(memberDao.findIdByEmail(certifyUtil.getEmailByToken(token)));
        Collection<WishProduct2> wishList2 = new ArrayList<>();
        for(WishProduct wishProduct : wishList) {
            Product product = productDao.findById(wishProduct.getProductId()).orElse(null);
            if(product != null) {
                wishList2.add(new WishProduct2(
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
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");

        if(!wishListValidation.isExistsProduct(requestBody.get("productId")))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not exists product.");

        wishListDao.save(
            new WishProduct(
                requestBody.get("memberId"),
                requestBody.get("productId")
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, Long id) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");

        wishListDao.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }
}
