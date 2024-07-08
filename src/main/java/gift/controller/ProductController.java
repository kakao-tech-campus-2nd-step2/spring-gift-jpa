package gift.controller;

import gift.DTO.ProductDTO;
import gift.domain.Product;
import gift.domain.Product.ProductSimple;
import gift.errorException.ListResult;
import gift.errorException.SingleResult;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping

    public ListResult<ProductDTO> getProductList() {
        return new ListResult<>(productService.getProductList());
    }

    @GetMapping("/simple")
    public ListResult<ProductSimple> getSimpleProductList() {
        return new ListResult<>(productService.getSimpleProductList());
    }

    @GetMapping("/{id}")
    public SingleResult<ProductDTO> getProduct(@PathVariable long id) {
        return new SingleResult<>(productService.getProduct(id));
    }

    @PostMapping
    public SingleResult<Integer> createProduct(@Valid @RequestBody Product.CreateProduct create) {
        SingleResult singleResult =  new SingleResult<>(productService.createProduct(create));
        singleResult.setErrorCode(HttpStatus.CREATED.value());
        return singleResult;
    }

    @PutMapping("/{id}")
    public SingleResult<Integer> updateProduct(@Valid @RequestBody Product.UpdateProduct update,
        @PathVariable long id) {

        SingleResult singleResult =  new SingleResult<>(productService.updateProduct(update, id));
        singleResult.setErrorCode(HttpStatus.ACCEPTED.value());
        return singleResult;
    }

    @DeleteMapping("/{id}")
    public SingleResult<Integer> deleteProduct(@PathVariable long id) {
        return new SingleResult<>(productService.deleteProduct(id));

    }
}
