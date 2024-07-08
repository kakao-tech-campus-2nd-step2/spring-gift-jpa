package gift.product;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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

        Product product = new Product(newProduct.getName(), newProduct.getPrice(), newProduct.getImageUrl());
        productRepository.save(product);
        redirectAttributes.addAttribute("id", product.getId());

        return "redirect:/manager/products/{id}";
    }

    @PutMapping("/products/update/{id}")
    @Transactional
    public String updateProduct(@PathVariable Long id, @Valid @ModelAttribute("product") ProductDTO product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (bindingResult.hasErrors()) {
            return "UpdateProduct";
        }

        Optional<Product> productFound = productRepository.findById(id);
        productFound.ifPresent(product1 -> {
            product1.setName(product.getName());
            product1.setPrice(product.getPrice());
            product1.setImageUrl(product.getImageUrl());
        });

        redirectAttributes.addAttribute("id", id);
        return "redirect:/manager/products/{id}";
    }

    @DeleteMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);

        if(product.isPresent()){
            productRepository.deleteById(id);
        }
        return "redirect:/manager/products";
    }

    @GetMapping("/products")
    public String getProductsView(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "ManageProduct";
    }

    @GetMapping("/products/add")
    public String addProductView(Model model){
        model.addAttribute("newProduct", new Product());
        return "AddProduct";
    }

    @GetMapping("/products/update/{id}")
    public String updateProductView(@PathVariable Long id, Model model){
        Optional<Product> product = productRepository.findById(id);
        if(product.isPresent()){
            ProductDTO productDTO = new ProductDTO(product.get());
            model.addAttribute("product",productDTO);
        }
        return "UpdateProduct";
    }

    @GetMapping("/products/{id}")
    public String getProduct(@PathVariable long id, Model model) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(value -> model.addAttribute("product", value));
        return "ProductInfo";
    }
}
