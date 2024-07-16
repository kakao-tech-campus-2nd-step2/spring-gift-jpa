package gift.controller;

import gift.domain.Product;
import gift.dto.ProductDto;
import gift.repositories.ProductRepository;
import gift.services.ProductService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    ProductRepository productRepository;

    private final ProductService productService;

    public PageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/") // 주소 매핑
    public String indexPageGet(Model model,
        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.DESC) Pageable pageable) {
        Page<ProductDto> products = productService.getAllProducts(pageable);
        int totalPages = products.getTotalPages();

        // 총 페이지 수가 0일 때 1로 설정
        if (totalPages == 0) {
            totalPages = 1;
        }
        int nowPage = products.getPageable().getPageNumber() + 1;
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5, totalPages);

        model.addAttribute("products", products);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "index";
    }

    @GetMapping("/add") // 주소 매핑
    public String addPageGet(Model model) {
        Product product = new Product(null, "", 0.0, "");
        model.addAttribute("product", product);
        return "add";
    }

    @GetMapping("/update") // 주소 매핑
    public String updatePageGet(@RequestParam Long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) {
            product = Optional.of(new Product(null, "", 0.0, ""));
        }
        model.addAttribute("product", product);
        return "update";
    }
}
