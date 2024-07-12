package gift.service;

import gift.model.Product;
import gift.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    //전체 조회
    public Page<Product> getAllProducts(int page, String sortBy, String sortOrder){
        Sort sort = getSort(sortBy, sortOrder);
        Pageable pageable = PageRequest.of(page, 10, sort);
        return productRepository.findAll(pageable);
    }

    private Sort getSort(String sortBy, String sortOrder){
        Sort sort = Sort.by(sortBy);
        if(sortOrder.equals("desc")){
            return sort.descending();
        }
        return sort;
    }

    //하나 조회
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
    }

    //저장
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    //삭제
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public void updateProduct(Long id, Product newProduct) {
        Product oldProduct = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 상품이 없습니다."));
        Product updatedProduct = oldProduct.update(
                newProduct.getName(),
                newProduct.getPrice(),
                newProduct.getImageUrl());
        productRepository.save(updatedProduct);
    }

    public int getPreviousPage(Page<Product> productPage) {
        if (productPage.hasPrevious()) {
            Pageable previousPageable = productPage.previousPageable();
            return previousPageable.getPageNumber();
        }
        return -1;
    }

    public int getNextPage(Page<Product> productPage) {
        if (productPage.hasNext()) {
            Pageable nextPageable = productPage.nextPageable();
            return nextPageable.getPageNumber();
        }
        return -1;
    }
}