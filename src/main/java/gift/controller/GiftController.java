package gift.controller;

import gift.dto.PageResponse;
import gift.model.gift.GiftRequest;
import gift.model.gift.GiftResponse;
import gift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public GiftResponse getGift(@PathVariable Long id) {
        return giftService.getGift(id);
    }

    @GetMapping
    public ResponseEntity<PageResponse<GiftResponse>> getAllGift(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                                                                 @RequestParam(value = "size", required = false, defaultValue = "5") int size) {
        PageResponse<GiftResponse> response = giftService.getAllGifts(page, size);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public void updateGift(@PathVariable Long id, @RequestBody GiftRequest giftReq) {
        giftService.updateGift(giftReq, id);
    }

    @DeleteMapping("/{id}")
    public void deleteGift(@PathVariable Long id) {
        giftService.deleteGift(id);
    }
}