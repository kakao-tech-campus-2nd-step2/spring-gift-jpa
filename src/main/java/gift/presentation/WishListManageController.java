package gift.presentation;

import gift.application.ProductService;
import gift.domain.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/wishlist")
public class WishListManageController {

    private final ProductService productService;

    @Autowired
    public WishListManageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getProducts(Model model) {
        List<Product> productList = productService.getProduct();
        model.addAttribute("products", productList);
        model.addAttribute("newProduct", new CreateProductRequestDTO("", 0.0, ""));
        model.addAttribute("pageSize", productList.size());
        model.addAttribute("totalEntries", productList.size());
        return "wishlist.html";
    }

    @PostMapping("")
    public String addProduct(@ModelAttribute CreateProductRequestDTO createProductRequestDTO) {
        productService.addProduct(createProductRequestDTO);
        return "redirect:/wishlist";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    public record CreateProductRequestDTO(String name, Double price, String imageUrl) {

    }
}
