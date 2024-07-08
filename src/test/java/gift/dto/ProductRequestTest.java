package gift.dto;

import static org.assertj.core.api.Assertions.assertThat;

import gift.dto.product.ProductRequest;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductRequestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("유효한 상품 추가")
    public void testAddProductValid() {
        ProductRequest productDTO = new ProductRequest(null, "Valid Name", 100, "valid.jpg");
        ResponseEntity<ProductRequest> response = restTemplate.postForEntity("/api/products", productDTO, ProductRequest.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("이름이 긴 상품 추가")
    public void testAddProductNameTooLong() {
        ProductRequest productDTO = new ProductRequest(null, "This name is definitely too long", 100, "valid.jpg");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/products", productDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("name", "상품 이름은 공백을 포함하여 최대 15자까지 입력할 수 있습니다.");
    }

    @Test
    @DisplayName("이름에 유효하지 않은 문자가 포함된 상품 추가")
    public void testAddProductInvalidCharactersInName() {
        ProductRequest productDTO = new ProductRequest(null, "Invalid@Name!", 100, "valid.jpg");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/products", productDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("name", "상품 이름에는 다음 특수 문자의 사용만 허용됩니다: ( ), [ ], +, -, &, /, _");
    }

    @Test
    @DisplayName("이름에 '카카오'가 포함된 상품 추가")
    public void testAddProductInvalidNameContainsKakao() {
        ProductRequest productDTO = new ProductRequest(null, "카카오톡", 100, "valid.jpg");
        ResponseEntity<Map> response = restTemplate.postForEntity("/api/products", productDTO, Map.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).containsEntry("name", "\"카카오\"가 포함된 문구는 담당 MD와 협의한 경우에만 사용할 수 있습니다.");
    }
}
