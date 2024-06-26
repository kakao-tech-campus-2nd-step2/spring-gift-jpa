package gift.controller;

import gift.domain.Product;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    // DB가 없으니까, 컬렉션을 사용하여 메모리에 저장
    private final Map<Long, Product> producsts = new HashMap<>();


}
