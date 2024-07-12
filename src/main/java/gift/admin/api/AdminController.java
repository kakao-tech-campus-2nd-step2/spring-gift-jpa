package gift.admin.api;

import gift.pagination.dto.PageResponse;
import gift.product.api.ProductController;
import gift.product.dto.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final ProductController productController;

    @Autowired
    public AdminController(ProductController productController) {
        this.productController = productController;
    }

    // 상품 조회
    @GetMapping
    public String getAllProducts(Model model,
                                 @PageableDefault(
                                         sort = "id",
                                         direction = Sort.Direction.DESC)
                                 Pageable pageable) {
        Page<ProductResponse> pagedProducts = productController.getPagedProducts(pageable);
        PageResponse pageInfo = new PageResponse(pagedProducts);

        model.addAttribute("productList", pagedProducts.getContent());
        model.addAttribute("pageInfo", pageInfo);
        return "admin-product-list";
    }

    // 상품 추가 폼 표시
    @GetMapping("/add")
    public String showAddProductForm() {
        return "product-add-form";
    }

    // 상품 수정 폼 표시
    @GetMapping("/edit/{id}")
    public String updateProduct(Model model, @PathVariable("id") Long id) {
        model.addAttribute("product", productController.getProduct(id));
        return "product-edit-form";
    }

}
