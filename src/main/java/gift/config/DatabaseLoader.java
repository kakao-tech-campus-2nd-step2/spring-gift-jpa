package gift.config;


import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseLoader {


    @Bean
    public CommandLineRunner initData(ProductRepository repository) {
        return args -> {
            // 데이터베이스에 초기 데이터 삽입
            repository.save(new Product(8146027L, "아이스 카페 아메리카노 T" , 4500 , "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg"));
            repository.save(new Product(123456L , "아이스 카페 라테 T, 5500", 5500 ,"https://item.elandrs.com/upload/prd/orgimg/088/2005488088_0000001.jpg?w=750&h=&q=100"));
            repository.save(new Product(7891011L , "뜨거운 아이스 아메리카노 T", 6500, "https://dimg.donga.com/wps/NEWS/IMAGE/2017/02/06/82727038.1.jpg"));
        };
    }
}
