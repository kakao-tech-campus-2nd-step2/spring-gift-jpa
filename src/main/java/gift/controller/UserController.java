package gift.controller;

import gift.DTO.Token;
import gift.DTO.UserDTO;
import gift.security.JwtTokenProvider;
import gift.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider){
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    /*
     * 로그인
     * 입력 받은 user와 DB 내의 user의 email로 생성한 token을 비교
     * 성공시 : 200 OK 및 User 정보로 만든 Token 반환
     */
    @PostMapping("/login")
    public ResponseEntity<Token> giveToken(@RequestBody UserDTO user){
        if(!userService.login(user)){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Token token = jwtTokenProvider.makeToken(user);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }
    /*
     * 회원가입
     * 회원가입 성공시 : 201 Created
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody UserDTO user){
        if(userService.isDuplicate(user))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        userService.createUser(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    /*
     * 모든 User의 정보 가져오기
     */
    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> readUsers() {
        List<UserDTO> all = userService.findAll();

        return new ResponseEntity<>(all, HttpStatus.OK);
    }
    /*
     * 유저 정보 수정하기
     */
    @PutMapping("/users/{userId}")
    public ResponseEntity<Void> updateUsers(@PathVariable("userId") String userId){
        if(!userService.isDuplicate(userService.loadOneUser(userId)))
            return new ResponseEntity<>(HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    /*
     * 유저 정보 삭제하기
     */
    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> deleteUsers(@PathVariable("userId") String userId){
        userService.delete(userId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
