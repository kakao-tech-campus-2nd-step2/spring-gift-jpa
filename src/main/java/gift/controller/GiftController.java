package gift.controller;

import gift.model.gift.GiftRequest;
import gift.model.gift.GiftResponse;
import gift.service.GiftService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<GiftResponse> getAllGift(@PageableDefault(sort = "id", direction = Sort.Direction.ASC, size = 5) Pageable pageable) {
        return giftService.getAllGifts(pageable);
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