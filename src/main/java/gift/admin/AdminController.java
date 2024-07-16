package gift.admin;

import gift.product.dto.ProductDto;
import gift.product.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final ProductService productService;

    @Autowired
    public AdminController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/list")
    public String listProducts(
            @RequestParam(defaultValue = "0") int page, // 쿼리 파라미터 page를 받아 페이지 번호 설정, 기본값은 0
            @RequestParam(defaultValue = "10") int size, // 쿼리 파라미터 size를 받아 페이지 크기 설정, 기본값은 10
            @RequestParam(defaultValue = "name,asc") String[] sort, Model model) // 뷰에 데이터를 전달하기 위한 모델 객체
    {
        Sort.Direction direction = Sort.Direction.fromString(sort[1]); // sort 배열의 두 번째 요소 정렬 방향을 Sort.Direction 객체로 변환
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0])); // 페이지 번호, 페이지 크기, 정렬 기준 및 방향 -> Pageable 객체 생성

        Page<ProductDto> productPage = productService.findAll(pageable);
        model.addAttribute("상품 목록", productPage.getContent()); // 현재 페이지의 상품 목록을 모델에 추가
        model.addAttribute("현재 페이지", page); // 현재 페이지 번호를 모델에 추가
        model.addAttribute("전체 페이지", productPage.getTotalPages()); // 총 페이지 수를 모델에 추가
        model.addAttribute("전체 항목", productPage.getTotalElements()); // 총 항목 수를 모델에 추가

        return "list"; // list.html 파일 보여주기
    }

    @GetMapping("/products/view/{product_id}")
    public String viewProduct(@PathVariable Long product_id, Model model) {
        ProductDto product = productService.findById(product_id);
        model.addAttribute("product", product);
        return "view"; // view.html 파일 보여주기
    }

    @GetMapping("/products/add")
    public String showAddProductForm(Model model) {
        model.addAttribute("productDto", new ProductDto());
        return "add"; // add.html 파일 보여주기
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add"; // 에러가 있으면 다시 add.html 보여주기
        }
        productService.save(productDto);
        return "redirect:/admin/products/list";
    }

    @GetMapping("/products/edit/{product_id}")
    public String showEditProductForm(@PathVariable Long product_id, Model model) {
        ProductDto product = productService.findById(product_id); // productService를 사용하여 id로 상품 찾기

        model.addAttribute("product", product); // 모델에 상품 추가

        return "edit"; // 렌더링할 뷰의 이름 반환
    }

    @PostMapping("/products/edit/{product_id}")
    public String editProduct(@PathVariable Long product_id, @Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result) {
        if (result.hasErrors()) {
            return "edit"; // 에러가 있으면 다시 edit.html 보여주기
        }
        productService.update(product_id, productDto);
        return "redirect:/admin/products/list";
    }
}