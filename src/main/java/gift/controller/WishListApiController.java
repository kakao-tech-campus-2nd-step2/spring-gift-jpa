package gift.controller;

import gift.auth.LoginMember;
import gift.request.LoginMemberDto;
import gift.response.ProductResponse;
import gift.request.WishListRequest;
import gift.exception.InputException;
import gift.model.Product;
import gift.model.WishProduct;
import gift.repository.WishProductDao;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WishListApiController {

    private final WishProductDao wishProductDao;

    public WishListApiController(WishProductDao wishProductDao) {
        this.wishProductDao = wishProductDao;
    }


    @GetMapping("/api/wishlist")
    public ResponseEntity<List<ProductResponse>> getWishList(@LoginMember LoginMemberDto memberDto) {

        List<Product> wishlist = wishProductDao.findAll(memberDto.id());
        if (wishlist.isEmpty()) {
            return new ResponseEntity<>( new ArrayList<>(), HttpStatus.OK);
        }

        List<ProductResponse> dtoList = wishlist.stream()
            .map(ProductResponse::new)
            .toList();
        return new ResponseEntity<>(dtoList, HttpStatus.OK);
    }

    @PostMapping("/api/wishlist")
    public ResponseEntity<Product> addWishList(@LoginMember LoginMemberDto memberDto,
        @RequestBody @Valid WishListRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        WishProduct wishProduct = new WishProduct(memberDto.id(), dto.productId());
        wishProductDao.insert(wishProduct);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/api/wishlist")
    public ResponseEntity<Product> deleteWishList(@LoginMember LoginMemberDto memberDto,
        @RequestBody @Valid WishListRequest dto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InputException(bindingResult.getAllErrors());
        }

        WishProduct wishProduct = new WishProduct(memberDto.id(), dto.productId());
        wishProductDao.delete(wishProduct);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
