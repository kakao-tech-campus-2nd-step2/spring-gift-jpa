package gift.controller;

import gift.dto.ProductDTO;
import gift.entity.Product;
import gift.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
public class ProductController {
    @Autowired
    private ProductService productService;


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/api/products")
    public String getProducts() {
        String jsonProducts = productService.getJsonAllProducts();
        return jsonProducts;
    }

    @PostMapping("/api/products")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(@RequestBody ProductDTO.SaveDTO product) {
        productService.saveProduct(product);

    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/api/products/{Id}")
    public void deleteProduct(@PathVariable int Id) {
        productService.deleteProduct(Id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/products")
    public void modifyProduct(@RequestBody Product product) {
        productService.modifyProduct(product);
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @GetMapping("/api/product/{id}")
    public String getProduct(@PathVariable int Id) {
        String product = productService.getProductById(Id);
        return product;
    }
}

