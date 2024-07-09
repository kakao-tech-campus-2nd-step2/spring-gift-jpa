package gift.controller;

import gift.component.LoginMember;
import gift.model.Member;
import gift.model.Wish;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishes")
public class WishController {

    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getWishes(@LoginMember Member member) {
        List<Wish> wishes = wishService.getWishesByMemberId(member.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("wishes", wishes);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> addWish(@RequestBody Wish wish, @LoginMember Member member) {
        wish.setMember(member);
        Wish savedWish = wishService.addWish(wish);
        Map<String, Object> response = new HashMap<>();
        response.put("wish", savedWish);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> removeWish(@PathVariable Long id, @LoginMember Member member) {
        boolean removed = wishService.removeWish(id, member.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("removed", removed);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
