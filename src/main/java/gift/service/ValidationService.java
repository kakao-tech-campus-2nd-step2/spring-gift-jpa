package gift.service;

import gift.domain.Product;
import gift.dto.CreateProductDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ValidationService {
    // 상품 정보 다 적혔는지 유효성 검사
    public void validateProductDto(CreateProductDto productDto) {
        if (productDto.getName() == null || productDto.getPrice() == null || productDto.getDescription() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "상품 정보를 모두 입력해야 합니다.");
        }
    }

    // 상품 리스트 존재하는 지 유효성 검사
    public void validateProductList(List<Product> products) {
        if (products.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "등록된 상품이 없습니다.");
        }
    }

    // 특정 상품 존재하는 지 유효성 검사
    public void validateProduct(Product product) {
        if (product == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 id를 가진 상품이 없습니다.");
        }
    }

}
