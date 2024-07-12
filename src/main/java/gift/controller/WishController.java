package gift.controller;

import gift.config.LoginMember;
import gift.domain.member.Member;
import gift.request.WishCreateRequest;
import gift.response.ProductResponse;
import gift.service.WishService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/wishes")
@RestController
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponse>> productList(@LoginMember Member member,
                                                             @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<ProductResponse> products = wishService.getProducts(member, pageable);

        return ResponseEntity.ok()
                .body(products);
    }

    @PostMapping
    public ResponseEntity<Void> productAdd(@LoginMember Member member,
                                           @RequestBody WishCreateRequest request) {
        wishService.addProduct(member, request.getProductId());

        return ResponseEntity.status(HttpStatus.CREATED)
                .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> productRemove(@LoginMember Member member,
                                              @RequestBody WishCreateRequest request) {
        wishService.removeProduct(member, request.getProductId());

        return ResponseEntity.ok()
                .build();
    }

}
