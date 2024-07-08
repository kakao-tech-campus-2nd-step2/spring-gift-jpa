package gift.domain.product.controller;

import gift.domain.product.dao.ProductDao;
import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductDao productDao;

    public ProductController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @GetMapping("/new")
    public String renderingNewForm() {
        return "new-product";
    }

    @GetMapping("/edit/{productId}")
    public String renderingEditForm(@PathVariable("productId") long productId, Model model) {
        Optional<Product> product = productDao.findById(productId);

        if (product.isEmpty()) {
            return "error";
        }
        model.addAttribute("product", product.get());

        return "edit-product";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid ProductDto productDto) {
        Product product = productDto.toProduct();
        Product savedProduct = productDao.insert(product);

        return "redirect:/products/" + savedProduct.getId();
    }

    @GetMapping
    public String readAll(Model model) {
        List<Product> productList = productDao.findAll();
        model.addAttribute("products", productList);
        return "products";
    }

    @GetMapping("/{productId}")
    public String readById(@PathVariable("productId") long productId, Model model) {
        Optional<Product> product = productDao.findById(productId);

        if (product.isEmpty()) {
            return "error";
        }
        model.addAttribute("product", product.get());

        return "product";
    }

    @PutMapping("/{productId}")
    public String update(@PathVariable("productId") long productId, @ModelAttribute @Valid ProductDto productDto) {

        Product product = productDto.toProduct();
        product.setId(productId);

        Optional<Product> updatedProduct = productDao.update(product);

        if (updatedProduct.isEmpty()) {
            return "error";
        }

        return "redirect:/products/" + updatedProduct.get().getId();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> delete(@PathVariable("productId") long productId) {
        int nOfRowsAffected = productDao.delete(productId);

        if (nOfRowsAffected != 1) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
