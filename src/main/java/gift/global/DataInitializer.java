package gift.global;

import gift.domain.entity.Product;
import gift.domain.entity.User;
import gift.domain.entity.Wish;
import gift.domain.repository.ProductRepository;
import gift.domain.repository.UserRepository;
import gift.domain.repository.WishRepository;
import gift.global.util.HashUtil;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

@Configuration
public class DataInitializer {

    @Bean
    ApplicationRunner init(ProductRepository product, UserRepository user, WishRepository wish) {
        return args -> insertInitialData(product, user, wish);
    }

    @Transactional
    public void insertInitialData(ProductRepository productRepository, UserRepository userRepository, WishRepository wishRepository) {
        Product[] products = {
            new Product("아이스 카페 아메리카노 T", 4500L, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"),
            new Product("제로 펩시 라임 355ml", 2300L, "https://img.danawa.com/prod_img/500000/193/555/img/13555193_1.jpg?shrink=330:*&_v=20230222093241"),
            new Product("오예스 12개입 360g", 3700L, "https://img.danawa.com/prod_img/500000/965/117/img/10117965_1.jpg?shrink=330:*&_v=20191210171250"),
            new Product("농심 육개장 사발면 소", 990L, "https://i.namu.wiki/i/ydm9GPPnZldqoMbVcl-pVaodrUu6VBedp_vyZnrnn2WrYBvESNYo1BB2g7cK_w8b2Mw-C66pRScUfEJT3sIMrw.webp"),
            new Product("바나나맛 우유 240ml", 1700L, "https://img.danawa.com/prod_img/500000/107/815/img/3815107_1.jpg?_v=20231212093346")};
        User[] users = {
            new User("admin@example.com", HashUtil.hashCode("admin"), "admin"),
            new User("user@example.com", HashUtil.hashCode("user"), "user"),
            new User("user2@example.com", HashUtil.hashCode("user"), "user")};

        for (int i = 0; i < products.length; i++) {
            products[i] = productRepository.save(products[i]);
        }
        for (int i = 0; i < users.length; i++) {
            users[i] = userRepository.save(users[i]);
        }

        wishRepository.save(new Wish(products[3], users[1], 5L));
        wishRepository.save(new Wish(products[0], users[1], 2L));
        wishRepository.save(new Wish(products[2], users[2], 4L));
        wishRepository.save(new Wish(products[4], users[2], 1L));
    }
}
