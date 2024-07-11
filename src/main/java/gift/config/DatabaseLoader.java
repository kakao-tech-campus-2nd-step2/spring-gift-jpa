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
            repository.save(new Product(456654L , "페이지 네이션 테스트1 T", 6500, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRgLNj5u1BHVCD_J17BNHQK499geyxJrmu-Ow&s"));
            repository.save(new Product(741598L , "페이지 네이션 테스트2 T", 7500, "https://mblogthumb-phinf.pstatic.net/MjAyMjAxMjVfMjAy/MDAxNjQzMTAyOTk2NjE0.gw_H_jjBM64svaftcnheR6-mHHlmGOyrr6htAuxPETsg.8JJSQNEA5HX2WmrshjZ-VjmJWqhmgE40Qm5csIud9VUg.JPEG.minziminzi128/IMG_7374.JPG?type=w800"));
            repository.save(new Product(5458745L , "페이지 네이션 테스트3 T", 8500, "https://mblogthumb-phinf.pstatic.net/MjAyMjAyMTZfMjI4/MDAxNjQ1MDEyNDk0OTkw.4G82ZWMDXJ62ye7hB-5X3CR2-dbNMjsvKvWedbAsp0cg.cFiYbBoVxJctdjdBi-fJ2pCAkZjfVgh07fiVRDZMxkAg.JPEG.minziminzi128/IMG_8491.JPG?type=w800"));
        };
    }
}
