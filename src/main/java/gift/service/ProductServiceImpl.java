package gift.service;

import gift.database.JpaProductRepository;
import gift.dto.ProductDTO;
import gift.exceptionAdvisor.ProductServiceException;
import gift.model.Product;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    private JpaProductRepository jpaProductRepository;

    public ProductServiceImpl(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public List<ProductDTO> readAll() {
        return jpaProductRepository.findAll().stream().map(
            product -> new ProductDTO(product.getId(), product.getName(), product.getPrice(),
                product.getImageUrl())).toList();
    }

    //새로운 상품 추가
    @Override
    public void create(ProductDTO dto) {
        checkKakao(dto.getName());
        Product product = new Product(null, dto.getName(), dto.getPrice(), dto.getImageUrl());
        jpaProductRepository.save(product);
    }


    @Override
    public void updateName(long id, String name) {
        var prod = getProduct(id);
        prod.setName(name);


    }

    @Override
    public void updatePrice(long id, int price) {
        var prod = getProduct(id);
        prod.setPrice(price);

    }

    @Override
    public void updateImageUrl(long id, String url) {
        var prod = getProduct(id);
        prod.setImageUrl(url);

    }

    @Override
    public void delete(long id) {
        jpaProductRepository.deleteById(id);
    }

    private void checkKakao(String productName) {
        if (productName.contains("카카오")) {
            throw new ProductServiceException("카카오 문구는 md협의 이후 사용할 수 있습니다.",
                HttpStatus.BAD_REQUEST);
        }
    }

    private Product getProduct(long id) {
        var prod = jpaProductRepository.findById(id).orElseThrow(
            () -> new ProductServiceException("상품이 존재하지 않습니다", HttpStatus.BAD_REQUEST));
        checkKakao(prod.getName());
        return prod;
    }

    @Override
    public List<ProductDTO> readProduct(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return jpaProductRepository.findAll(pageable).stream().map(product ->
            new ProductDTO(product.getId(), product.getName(), product.getPrice(), product.getImageUrl()))
            .toList();
    }
}
