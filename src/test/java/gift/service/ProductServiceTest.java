package gift.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import gift.DTO.ProductDTO;
import gift.domain.Product;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private List<Product.CreateProduct> list = new ArrayList<>();
    private ProductDTO productDTO;

    @BeforeEach
    public void setUp() {
        list.add(new Product.CreateProduct("test1", 100, "test1"));
        list.add(new Product.CreateProduct("test2", 100, "test2"));
        list.add(new Product.CreateProduct("test3", 100, "test3"));

        for (Product.CreateProduct p : list) {
            productService.createProduct(p);
        }
    }

    @Test
    @DisplayName("상품 생성")
    public void CreateProduct() {
        //  how
        Product.CreateProduct temp = new Product.CreateProduct("test4", 100, "test4");
        //  when
        productService.createProduct(temp);

        //    가장 마지막 상품 가져오기
        productDTO = productService.getProduct((long) productService.getProductList().size() - 1);

        //  then
        //    추가한 상품의 이름과 마지막에 위치한 상품의 이름 비교
        assertEquals(temp.getName(), productDTO.getName());
    }

    @Test
    @DisplayName("상품 수정")
    public void UpdateProduct() {
        //  how
        Product.UpdateProduct temp = new Product.UpdateProduct("test4-1", 100, "test4-1");
        //  when
        productService.updateProduct(temp, (long) productService.getProductList().size() - 1);

        //    가장 마지막 상품 가져오기
        productDTO = productService.getProduct((long) productService.getProductList().size() - 1);

        //  then
        //    수정한 상품의 이름과 마지막에 위치한 상품의 이름 비교
        assertEquals(temp.getName(), productDTO.getName());
    }

    @Test
    @DisplayName("상품 삭제")
    public void removeProduct() {
        //  how
        int before = productService.getProductList().size();
        Long target = (long) before - 1;
        //  when
        productService.deleteProduct(target);
        //  then
        //    삭제 전후의 크기 비교
        assertEquals(before - 1, productService.getProductList().size());
    }

}