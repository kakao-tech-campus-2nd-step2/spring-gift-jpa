package gift.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import gift.dto.ProductDTO;
import gift.entity.Option;
import gift.entity.Product;
import gift.exception.exception.BadRequestException;
import gift.exception.exception.UnAuthException;
import gift.exception.exception.NotFoundException;
import gift.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Arrays;
import java.util.List;

@Service
@Validated
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<ProductDTO> products = productRepository.getAllProduct();
        return products;
    }

    public String getJsonAllProducts(){
        ObjectMapper objectMapper = new ObjectMapper();
        List<ProductDTO> products = productRepository.getAllProduct();
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
            productRepository.saveProduct(saveProduct);
        }

        List<String> optionList = Arrays.stream(product.getOption().split(",")).toList();
        for(String str : optionList){
            Option option = new Option(product.getId(), str);

            if(isValidOption(option))
                productRepository.saveOption(option);

        }
    }

    private boolean isValidProduct(@Valid Product product){
        if(product.getName().contentEquals("카카오"))
            throw new UnAuthException("MD와 상담해주세요.");
        if(productRepository.isExistProduct(product)){
            Product product1 = productRepository.findProductByID(product.getId());
            return product.equals(product1);
        }
        return true;
    }

    private boolean isValidOption(@Valid Option option){
        if(productRepository.isExistOption(option))
            throw new BadRequestException("이미 존재하는 옵션입니다.");
        return true;
    }

    public void deleteProduct(int id) {
        if(productRepository.findProductByID(id)==null)
            throw new NotFoundException("존재하지 않는 id입니다.");
        productRepository.deleteProductByID(id);
        productRepository.deleteOptionsByID(id);
    }


    public String getProductByID(int id) {
        Product product = productRepository.findProductByID(id);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonProduct="";
        try {
            jsonProduct = objectMapper.writeValueAsString(product);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(jsonProduct==null)
            throw new NotFoundException("해당 물건이 없습니다.");
        return jsonProduct;
    }

    public void modifyProduct(Product product) {
        productRepository.updateProduct(product);
    }

}
