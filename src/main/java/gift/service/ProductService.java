package gift.service;

import gift.dto.CreateProduct;
import gift.dto.EditProduct;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Long createProduct(CreateProduct.Request request) {
        checkValidProductName(request.getName());
        return productRepository.insert(request);
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Product getOneById(int id) {
        return productRepository.findOneById(id);
    }

    public void update(int id, EditProduct.Request request) {
        checkValidProductName(request.getName());
        productRepository.update(id, request);
    }

    public void delete(int id) {
        productRepository.delete(id);
    }

    // 이름 유효성 검사 코드
    public boolean checkValidProductName(String name) {
        boolean result = true;
        if (!(checkValidLength(name, 1, 15))) {
            result = false;
            throw new IllegalProductNameLengthException();
        }
        if (!(checkValidSpecialCharacter(name))) {
            result = false;
            throw new IllegalProductNameCharacterException();
        }
        if (!(checkNotContainKeyword(name, "카카오"))) {
            result = false;
            throw new IllegalProductNameKeywordException();
        }
        return result;
    }

    private boolean checkValidLength(String name, int start, int end) {
        return (name.length() >= 1 && name.length() <= end);
    }

    private boolean checkValidSpecialCharacter(String name) {
        boolean result = true;

        char[] nameWords = name.toCharArray();
        for (char word : nameWords) {
            if (checkSpecialCharacter(word) && (!checkAllowedSpecialCharacter(word))) {
                result = false;
            }
        }
        return result;
    }

    private boolean checkAllowedSpecialCharacter(char word) {
        char[] allowedSpecialCharacters = {'(', ')', '[', ']', '+', '-', '&', '/', '_'};
        for (int allowedSpecialCharacter : allowedSpecialCharacters) {
            if (word == allowedSpecialCharacter) {
                return true;
            }
        }
        return false;
    }

    private boolean checkSpecialCharacter(char word) {
        boolean result = true;
        if ((word >= 'a' && word <= 'z') ||
                (word >= 'A' && word <= 'Z') ||
                (word >= '0' && word <= '9') ||
                (word >= '가' && word <= '힣') ||
                (word == ' ')) {
            result = false;
        }
        return result;
    }

    private boolean checkNotContainKeyword(String name, String keyword) {
        return !(name.contains(keyword));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({IllegalProductNameLengthException.class, IllegalProductNameCharacterException.class, IllegalProductNameKeywordException.class})
    public ResponseEntity<String> handleProductNameExceptions(RuntimeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    public class IllegalProductNameLengthException extends IllegalArgumentException {
        public IllegalProductNameLengthException() {
            super("상품 이름은 1 ~ 15 글자로 입력해주세요.");
        }
    }

    public class IllegalProductNameCharacterException extends IllegalArgumentException {
        public IllegalProductNameCharacterException() {
            super("상품 이름에서 특수문자는 ()[] + - & / _ 만 사용 가능합니다.");
        }
    }

    public class IllegalProductNameKeywordException extends IllegalArgumentException {
        public IllegalProductNameKeywordException() {
            super("상품이름에 \"카카오\" 키워드를 포함시키고 싶으신 경우, 담당MD와의 협의가 필요합니다. 고객센터로 문의주세요.");
        }
    }
}
