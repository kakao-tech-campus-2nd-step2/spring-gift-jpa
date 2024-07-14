package gift.controller;

import gift.dto.PageRequestDTO;
import gift.service.ProductService;
import gift.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public String getAllProducts(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "id") String sortBy,
                                 @RequestParam(defaultValue = "asc") String sortOrder, Model model) {
        PageRequestDTO pageRequestDTO = new PageRequestDTO(page, sortBy, sortOrder);
        Page<Product> productPage = productService.getAllProducts(pageRequestDTO);

        model.addAttribute("productList", productPage.getContent());

        int previousPage = productService.getPreviousPage(productPage);
        model.addAttribute("previousPage", previousPage);

        int nextPage = productService.getNextPage(productPage);
        model.addAttribute("nextPage", nextPage);

        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortOrder", sortOrder);

        return "index";
    }

    @GetMapping("/{id}")
    @ResponseBody
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public String postProduct(@ModelAttribute Product product, Model model) {
        try{
            productService.saveProduct(product);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
        model.addAttribute("error", e.getMessage());
        return "error";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }

    @PostMapping("/update/{id}")
    public String updateProduct(@PathVariable Long id, @ModelAttribute Product newProduct, Model model) {
        try{
            productService.updateProduct(id, newProduct);
            return "redirect:/products";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "error";
        }
    }
}