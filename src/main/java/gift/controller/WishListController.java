package gift.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gift.service.WishListService;
import gift.dto.MemberDto;
import gift.dto.WishListDto;
import gift.dto.request.WishListRequest;
import gift.util.JwtUtil;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/wishlist")
public class WishListController {
    
    private WishListService wishListService;
    private JwtUtil jwtUtil;

    public WishListController(WishListService wishListService, JwtUtil jwtUtil){
        this.wishListService = wishListService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping
    public ResponseEntity<Page<WishListDto>> getWishList(@RequestHeader("Authorization") String authorizationHeader, MemberDto memberDto, @RequestParam(defaultValue = "0") int page){
        if (!jwtUtil.validateToken(authorizationHeader, memberDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Page<WishListDto> wishListPage = wishListService.findWishListById(jwtUtil.extractToken(authorizationHeader), page);
        return new ResponseEntity<>(wishListPage, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> addWishList(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody WishListRequest wishListRequest, MemberDto memberDto){
        
        if (!jwtUtil.validateToken(authorizationHeader, memberDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        wishListService.addWishList(jwtUtil.extractToken(authorizationHeader), wishListRequest.getProductId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteWishList(@RequestHeader("Authorization") String authorizationHeader, @Valid @RequestBody WishListRequest wishListRequest, MemberDto memberDto){
        
        if (!jwtUtil.validateToken(authorizationHeader, memberDto)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        wishListService.deleteWishList(jwtUtil.extractToken(authorizationHeader), wishListRequest.getProductId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
