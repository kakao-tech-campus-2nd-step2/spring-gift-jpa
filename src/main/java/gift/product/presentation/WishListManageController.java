package gift.product.presentation;

import gift.product.application.ProductService;
import gift.product.domain.Product;
import gift.util.CommonResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishlist")
public class WishListManageController {

    @Autowired
    private ProductService productService;


    @GetMapping("")
    public ResponseEntity<CommonResponse<List<Product>>> getProducts() {
        List<Product> productList = productService.getProduct();
        return ResponseEntity.ok(new CommonResponse<>(productList, "상품 조회가 정상적으로 완료되었습니다", true));
    }

    @PostMapping("")
    public ResponseEntity<CommonResponse<Long>> addProduct(
        @RequestBody CreateProductRequestDTO createProductRequestDTO) {
        Long productId = productService.addProduct(createProductRequestDTO);
        return ResponseEntity.ok(new CommonResponse<>(productId, "상품이 정상적으로 추가 되었습니다", true));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(new CommonResponse<>(null, "상품이 정상적으로 삭제 되었습니다", true));
    }

    public record CreateProductRequestDTO(String name, Double price, String imageUrl) {

    }
}
