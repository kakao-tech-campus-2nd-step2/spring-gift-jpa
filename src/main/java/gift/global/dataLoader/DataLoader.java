package gift.global.dataLoader;

import gift.domain.cart.CartItem;
import gift.domain.cart.JpaCartItemRepository;
import gift.domain.product.JpaProductRepository;
import gift.domain.product.Product;
import gift.domain.user.JpaUserRepository;
import gift.domain.user.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test") // 테스트 환경에서는 동작하지 않도록
public class DataLoader {

    private final JpaProductRepository jpaProductRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaCartItemRepository jpaCartItemRepository;

    @Autowired
    public DataLoader(JpaProductRepository jpaProductRepository,
        JpaUserRepository jpaUserRepository, JpaCartItemRepository jpaCartItemRepository) {
        this.jpaProductRepository = jpaProductRepository;
        this.jpaUserRepository = jpaUserRepository;
        this.jpaCartItemRepository = jpaCartItemRepository;
    }

    @PostConstruct
    public void init() {
        // Product
        Product americano = new Product("아이스 아메리카노 T", 4500, "https://example.com/image.jpg");
        Product cafuchino = new Product("아이스 카푸치노 M", 4700, "https://example.com/image.jpg");
        Product malcha = new Product("핫 말차라떼 L", 6800, "https://example.com/image.jpg");
        jpaProductRepository.save(americano);
        jpaProductRepository.save(cafuchino);
        jpaProductRepository.save(malcha);
        // dummy Product data
        for (int i = 0; i < 100; i++) {
            Product dummyProduct = new Product(
                "더미 제품 " + (i + 1),
                1000 + (i * 10),
                "https://example.com/dummy" + (i + 1) + ".jpg"
            );
            jpaProductRepository.save(dummyProduct);
        }

        // User
        User minji = new User("minji@example.com", "password1");
        User junseo = new User("junseo@example.com", "password2");
        User donghyun = new User("donghyun@example.com", "password3");
        jpaUserRepository.save(minji);
        jpaUserRepository.save(junseo);
        jpaUserRepository.save(donghyun);

        // CartItem
        jpaCartItemRepository.save(new CartItem(minji, malcha));
        jpaCartItemRepository.save(new CartItem(junseo, cafuchino));
        jpaCartItemRepository.save(new CartItem(donghyun, cafuchino));

    }
}