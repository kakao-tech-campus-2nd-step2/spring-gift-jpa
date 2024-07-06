package gift.product.presentation.controller;


import gift.product.presentation.dto.ResponseProductDto;
import gift.product.business.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products/manager")
public class ProductManagerController {

    private final ProductService productService;

    public ProductManagerController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String productManager(Model model) {
        var productDtoList = productService.getAllProducts();
        var responseProductDtoListproductList = ResponseProductDto.of(productDtoList);
        model.addAttribute("products", responseProductDtoListproductList);
        return "products-manager";
    }
}
