package gift.product;

import gift.util.PageUtil;
import jakarta.validation.Valid;
import java.util.Arrays;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProduct(Model model,
        @RequestParam(value = "page", required = false, defaultValue = "0") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size,
        @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
        @RequestParam(value = "sortDirection", required = false, defaultValue = "asc") String sortDirection) {
        size = PageUtil.validateSize(size);
        sortBy = PageUtil.validateSortBy(sortBy, Arrays.asList("id", "name"));
        Direction direction = PageUtil.validateDirection(sortDirection);
        Page<ProductDTO> paging = productService.getAllProducts(page, size, sortBy, direction);
        model.addAttribute("products", paging);
        model.addAttribute("currentPage", paging.getNumber());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", direction.toString());
        return "products";
    }

    @GetMapping("/add")
    public String showPostProduct(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        return "add";
    }

    @GetMapping("/update/{id}")
    public String showPutProduct(@PathVariable("id") long id, Model model)
        throws NotFoundException {
        ProductDTO product = productService.getProductById(id);
        model.addAttribute("productDTO", product);
        return "update";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable("id") long id) throws NotFoundException {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/add")
    public String postProduct(@Valid @ModelAttribute("productDTO") ProductDTO product,
        BindingResult result, Model model) {
        productService.existsByNamePutResult(product.getName(), result);
        if (result.hasErrors()) {
            return "add";
        }
        productService.addProduct(product);
        model.addAttribute("productDTO", product);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String putProduct(@PathVariable("id") Long id,
        @Valid @ModelAttribute("productDTO") ProductDTO product, BindingResult result)
        throws NotFoundException {
        productService.existsByNamePutResult(product.getName(), result);
        if (result.hasErrors()) {
            return "update";
        }
        ProductDTO product1 = new ProductDTO(id, product.getName(), product.getPrice(),
            product.getImageUrl());
        productService.updateProduct(product1);
        return "redirect:/products";
    }
}
