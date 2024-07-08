package gift.service;

import gift.authorization.JwtUtil;
import gift.entity.LoginUser;
import gift.entity.Wish;
import gift.repository.JdbcWishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;

@Service
public class WishService {

    private final JdbcWishRepository repository;
    private final JwtUtil jwtUtil;

    @Autowired
    public WishService(JdbcWishRepository repository, JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
        this.repository = repository;
    }

    public void addWish(long productId, LoginUser loginUser) {
        repository.addToWishlist(loginUser.getEmail(), loginUser.getType(), productId);
    }

    public void removeWish(long productId, LoginUser loginUser) {
        repository.removeFromWishlist(loginUser.getEmail(), loginUser.getType(), productId);
    }

    public List<Wish> getWishesByMemberId(LoginUser loginUser) {
        return repository.getWishlistItems(loginUser.getEmail());
    }

    public LoginUser getLoginUserByToken(NativeWebRequest webRequest){
        String token = webRequest.getHeader("Authorization").substring("Bearer ".length());
        if(jwtUtil.checkClaim(token)){
            String email = jwtUtil.getUserEmail(token);
            String type = jwtUtil.getUserType(token);
            LoginUser loginUser = new LoginUser(email ,type, token);
            return loginUser;
        }
        return null;
    }
}
