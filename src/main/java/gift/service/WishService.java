package gift.service;

import gift.dto.LoginUser;
import gift.dto.WishDTO;
import gift.entity.User;
import gift.entity.Wish;
import gift.exceptionhandler.UserException;
import gift.repository.ProductRepository;
import gift.repository.UserRepository;
import gift.repository.WishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WishService {

    private final WishRepository wishRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Autowired
    public WishService(WishRepository wishRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.wishRepository = wishRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Long findByEmail(String email){
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return user.get()
                    .getId();
        }
        throw new UserException("User not found");
    }

    public static Wish toEntity(WishDTO dto) {
        Wish wish = new Wish();
        wish.setMemberId(dto.memberId());
        wish.setProductId(dto.productId());
        return wish;
    }

    public void addWish(long productId, LoginUser loginUser) {
        String email = loginUser.getEmail();
        Long memberId = findByEmail(email);
        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductId(productId);
        wishRepository.save(wish);
    }

    public void removeWish(long productId, LoginUser loginUser) {
        String email = loginUser.getEmail();
        Long memberId = findByEmail(email);
        Wish wish = new Wish();
        wish.setMemberId(memberId);
        wish.setProductId(productId);
        wishRepository.deleteByMemberIdAndProductId(memberId, productId);
    }

    public List<Wish> getWishesByMemberId(LoginUser loginUser) {
        return wishRepository.findAll();
    }
}
