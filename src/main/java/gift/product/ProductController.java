package gift.product;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/manager")
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @PostMapping("/products/add")
    public String addProduct(@Valid @ModelAttribute("newProduct") ProductDTO newProduct, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "AddProduct";
        }

        System.out.println("add");
        Product product = productRepository.insertProduct(newProduct);
        redirectAttributes.addAttribute("id", product.getId());
        System.out.println(product.id);
        return "redirect:/manager/products/{id}";
    }

    @PutMapping("/products/update/{id}")
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDTO product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "UpdateProduct";
        }

        productRepository.updateProduct(id, product);
        redirectAttributes.addAttribute("id", id);
        return "redirect:/manager/products/{id}";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Product product = productRepository.selectProduct(id);

        if(product != null){
            productRepository.deleteProduct(id);
        }
        return "redirect:/manager/products";
    }

    @GetMapping("/products")
    public String getProductsView(Model model){
        model.addAttribute("products", productRepository.selectProducts());
        return "ManageProduct";
    }

    @GetMapping("/products/add")
    public String addProductView(Model model){
        model.addAttribute("newProduct", new Product());
        return "AddProduct";
    }

    @GetMapping("/products/update/{id}")
    public String updateProductView(@PathVariable Long id, Model model){
        model.addAttribute("product", new ProductDTO(productRepository.selectProduct(id)));
        return "UpdateProduct";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        Product product = productRepository.selectProduct(id);
        model.addAttribute("product", product);
        return "ProductInfo";
    }
}
