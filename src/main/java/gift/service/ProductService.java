package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.compositeKey.OptionId;
import gift.dto.ProductDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.UnAuthException;
import gift.exception.exception.NotFoundException;
import gift.repository.OptionRepository;
import gift.repository.ProductOptionRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Validated
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    WishListRepository wishListRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private ProductOptionRepository productOptionRepository;

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products;
    }

    public String getJsonAllProducts(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Product> products = productRepository.findAll();
        String jsonProduct="";
        try {
             jsonProduct = objectMapper.writeValueAsString(products);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonProduct;
    }

    public void saveProduct(ProductDTO product) {
        if(product.getOption() == null)
            throw new BadRequestException("하나의 옵션은 필요합니다.");

        Product saveProduct = new Product(product.getId(),product.getName(), product.getPrice(), product.getImageUrl());

        if(isValidProduct(saveProduct)){
            productRepository.save(saveProduct);
        }

        List<String> optionList = Arrays.stream(product.getOption().split(",")).toList();
        for(String str : optionList){
            OptionId optionId = new OptionId(product.getId(), str);

            if(isValidOption(optionId))
                optionRepository.save(new Option(optionId));

        }
    }

    private boolean isValidProduct(@Valid Product product){
        if(product.getName().contentEquals("카카오"))
            throw new UnAuthException("MD와 상담해주세요.");
        Optional<Product> productOptional = productRepository.findById(product.getId());
        return productOptional.map(value -> value.equals(product)).orElse(true);
    }

    private boolean isValidOption(@Valid OptionId optionID){
        if(optionRepository.findById(optionID).isPresent())
            throw new BadRequestException("이미 존재하는 옵션입니다.");
        return true;
    }

    public void deleteProduct(int id) {
        if(!productRepository.findById(id).isPresent())
            throw new NotFoundException("존재하지 않는 id입니다.");
        productRepository.deleteById(id);
        optionRepository.deleteByProductID(id);
    }


    public String getProductByID(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty())
            throw new NotFoundException("해당 물건이 없습니다.");
        Product product = optionalProduct.get();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct="";
        try {
            jsonProduct = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonProduct;
    }

    public void modifyProduct(Product product) {
        if(productRepository.findById(product.getId()).isEmpty())
            throw new NotFoundException("물건이없습니다.");
        productRepository.deleteById(product.getId());
        productRepository.save(product);
    }

    public List<ProductDTO> getProductWithOption() {
        return productOptionRepository.findProductWithOptions();
    }
}
