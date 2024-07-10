package gift.service;

import gift.domain.WishedProduct;
import gift.dto.WishedProductDTO;
import gift.exception.NoSuchProductException;
import gift.exception.NoSuchWishedProductException;
import gift.repository.ProductRepository;
import gift.repository.WishedProductRepository;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishedProductService {

    private final WishedProductRepository wishedProductRepository;
    private final ProductRepository productRepository;

    @Autowired
    public WishedProductService(WishedProductRepository wishedProductRepository, ProductRepository productDAO) {
        this.wishedProductRepository = wishedProductRepository;
        this.productRepository = productDAO;
    }

    public Collection<WishedProductDTO> findAll(String memberEmail) {
        return wishedProductRepository.findByMemberEmail(memberEmail)
            .stream()
            .map(wishedProduct -> wishedProduct.toDTO())
            .collect(Collectors.toList());
    }

    public WishedProductDTO save(String memberEmail, WishedProductDTO wishedProductDTO) {
        productRepository.findById(wishedProductDTO.productId()).orElseThrow(NoSuchProductException::new);
        WishedProduct wishedProduct = new WishedProduct(memberEmail, wishedProductDTO.productId(), wishedProductDTO.amount());
        return wishedProductRepository.save(wishedProduct).toDTO();
    }

    public WishedProductDTO delete(long id) {
        WishedProductDTO deletedWishedProductDTO = wishedProductRepository.findById(id)
            .orElseThrow(NoSuchWishedProductException::new)
            .toDTO();
        wishedProductRepository.deleteById(id);
        return deletedWishedProductDTO;
    }

    public WishedProductDTO update(long id, String memberEmail, WishedProductDTO wishedProductDTO) {
        productRepository.findById(wishedProductDTO.productId()).orElseThrow(NoSuchProductException::new);
        if (wishedProductDTO.amount() == 0) {
            return delete(id);
        }
        WishedProduct wishedProduct = new WishedProduct(id, memberEmail, wishedProductDTO.productId(), wishedProductDTO.amount());
        return wishedProductRepository.save(wishedProduct).toDTO();
    }
}
