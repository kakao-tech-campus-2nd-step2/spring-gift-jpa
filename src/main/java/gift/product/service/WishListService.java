package gift.product.service;

import gift.product.dao.WishListDao;
import gift.product.model.WishProduct;
import gift.product.util.CertifyUtil;
import gift.product.validation.WishListValidation;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    public WishListService(WishListDao wishListDao, CertifyUtil certifyUtil, WishListValidation wishListValidation) {
        this.wishListDao = wishListDao;
        this.certifyUtil = certifyUtil;
        this.wishListValidation = wishListValidation;
        wishListDao.createWishListTable();
    }

    public Collection<WishProduct2> getAllProducts(HttpServletRequest request) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        return wishListDao.getAllProducts(certifyUtil.getEmailByToken(token));
    }

    public ResponseEntity<String> registerWishProduct(HttpServletRequest request, Map<String, Long> requestBody) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");

        if(!wishListValidation.isExistsProduct(requestBody.get("productId")))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not exists product.");

        wishListDao.registerWishProduct(
            new WishProduct(
                idCounter.incrementAndGet(),
                requestBody.get("productId"),
                Math.toIntExact(requestBody.get("count")),
                certifyUtil.getEmailByToken(token)
            )
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("WishProduct registered successfully");
    }

    public ResponseEntity<String> updateCountWishProduct(HttpServletRequest request, Map<String, Long> requestBody, Long id) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");

        if(!wishListValidation.isRegisterProduct(requestBody.get("productId"), certifyUtil.getEmailByToken(token)))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("not exists product in wishlist");

        wishListDao.updateCountWishProduct(id, Math.toIntExact(requestBody.get("count")));
        return ResponseEntity.status(HttpStatus.CREATED).body("update WishProduct Count successfully");
    }

    public ResponseEntity<String> deleteWishProduct(HttpServletRequest request, Long id) {
        String token = certifyUtil.checkAuthorization(request.getHeader("Authorization"));
        if(token == null)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid");

        wishListDao.deleteWishProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("delete WishProduct successfully");
    }

    public boolean existsByPId(Long pId, String email) {
        return wishListDao.existsByPId(pId, email);
    }
}
