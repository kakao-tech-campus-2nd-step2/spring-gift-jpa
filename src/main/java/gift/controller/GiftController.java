package gift.controller;

import gift.dto.PagingRequest;
import gift.dto.PagingResponse;
import gift.model.gift.GiftRequest;
import gift.model.gift.GiftResponse;
import gift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/gifts")
public class GiftController {

    private GiftService giftService;

    @Autowired
    public GiftController(GiftService giftService) {
        this.giftService = giftService;
    }

    @PostMapping
    public ResponseEntity<String> addGift(@RequestBody GiftRequest giftReq) {
        giftService.addGift(giftReq);
        return ResponseEntity.status(HttpStatus.CREATED).body("Gift created");
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftResponse> getGift(@PathVariable Long id) {
        GiftResponse gift = giftService.getGift(id);
        return ResponseEntity.ok(gift);
    }

    @GetMapping
    public ResponseEntity<PagingResponse<GiftResponse>> getAllGift(@ModelAttribute PagingRequest pagingRequest) {
        PagingResponse<GiftResponse> response = giftService.getAllGifts(pagingRequest.getPage(), pagingRequest.getSize());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateGift(@PathVariable Long id, @RequestBody GiftRequest giftReq) {
        giftService.updateGift(giftReq, id);
        return ResponseEntity.ok("상품 수정이 완료되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGift(@PathVariable Long id) {
        giftService.deleteGift(id);
        return ResponseEntity.ok("상품 삭제가 완료되었습니다.");
    }
}