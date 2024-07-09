package gift.controller;

import gift.domain.Product;
import gift.domain.Product.ProductSimple;
import gift.entity.ProductEntity;
import gift.mapper.PageMapper;
import gift.service.ProductService;
import gift.util.page.PageResult;
import gift.util.page.SingleResult;
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
    public PageResult<ProductEntity> getProductList(@Valid Product.getList param) {
        return PageMapper.toPageResult(productService.getProductList(param));
    }

    @GetMapping("/simple")
    public PageResult<ProductSimple> getSimpleProductList(@Valid Product.getList param) {
        return PageMapper.toPageResult(productService.getSimpleProductList(param));
    }

    @GetMapping("/{id}")
    public SingleResult<ProductEntity> getProduct(@PathVariable long id) {
        return new SingleResult<>(productService.getProduct(id));
    }

    @PostMapping
    public SingleResult<Long> createProduct(@Valid @RequestBody Product.CreateProduct create) {
        SingleResult singleResult = new SingleResult<>(productService.createProduct(create));
        singleResult.setErrorCode(HttpStatus.CREATED.value());
        return singleResult;
    }

    @PutMapping("/{id}")
    public SingleResult<Long> updateProduct(@Valid @RequestBody Product.UpdateProduct update,
        @PathVariable long id) {

        SingleResult singleResult = new SingleResult<>(productService.updateProduct(update, id));
        singleResult.setErrorCode(HttpStatus.ACCEPTED.value());
        return singleResult;
    }

    @DeleteMapping("/{id}")
    public SingleResult<Long> deleteProduct(@PathVariable long id) {
        return new SingleResult<>(productService.deleteProduct(id));

    }
}
