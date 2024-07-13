package gift;

import gift.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductTest {

    @Test
    public void testProductGettersAndSetters() {
        Product product = new Product();
        product.update("아이스 카페 아메리카노 T", 4500, "https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg");
        assertEquals("아이스 카페 아메리카노 T", product.getName());
        assertEquals(4500, product.getPrice());
        assertEquals("https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg", product.getImageUrl());
    }

}
