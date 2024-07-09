package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.exception.MemberAuthorizationException;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public ResponseEntity<List<Wish>> getWishes(@LoginMember Member member) {
        if(member == null){
            throw new MemberAuthorizationException("Authorization failed");
        }
        return new ResponseEntity<>(wishService.getWishesByMember(member), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Wish> createWish(@RequestBody WishRequest wishRequest, @LoginMember Member member) {
        if (member == null) {
            throw new MemberAuthorizationException("Authorization failed");
        }
        Wish wish =wishService.addWish(wishRequest, member);

        return new ResponseEntity<>(wish, HttpStatus.CREATED);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Wish> deleteWish(@PathVariable("id") Long id, @LoginMember Member member) {
        if (member == null) {
            throw new MemberAuthorizationException("Authorization failed");
        }
        wishService.deleteWish(id, member);
        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(value= MemberAuthorizationException.class)
    public ResponseEntity<String> handleMemberException(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}