package gift.controller;

import gift.annotation.LoginMember;
import gift.domain.Member;
import gift.domain.Wish;
import gift.dto.WishRequest;
import gift.exception.MemberAuthenticationException;
import gift.service.WishService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishes")
public class WishController {
    private final WishService wishService;

    public WishController(WishService wishService) {
        this.wishService = wishService;
    }

    @GetMapping
    public List<Wish> getWishes(@LoginMember Member member) {
        if(member == null){
            throw new MemberAuthenticationException("Authentication failed");
        }
        return wishService.getWishesByMember(member);
    }

    @PostMapping
    public void createWish(@RequestBody WishRequest request, @LoginMember Member member) {
        if (member == null) {
            throw new MemberAuthenticationException("Authentication failed");
        }
        wishService.addWish(request, member);
    }

    @DeleteMapping("/{id}")
    public void deleteWish(@PathVariable("id") Long id, @LoginMember Member member) {
        if (member == null) {
            throw new MemberAuthenticationException("Authentication failed");
        }
        wishService.deleteWish(id, member);
    }

    @ExceptionHandler(value= MemberAuthenticationException.class)
    public ResponseEntity<String> handleMemberException(Exception e){
        return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}