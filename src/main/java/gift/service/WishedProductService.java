package gift.service;

import gift.domain.Product;
import gift.domain.WishedProduct;
import gift.dto.MemberDTO;
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

    public Collection<WishedProductDTO> getWishedProducts(MemberDTO memberDTO) {
        return wishedProductRepository.findByMember(memberDTO.toEntity())
            .stream()
            .map(wishedProduct -> wishedProduct.toDTO())
            .collect(Collectors.toList());
    }

    public WishedProductDTO addWishedProduct(MemberDTO memberDTO, WishedProductDTO wishedProductDTO) {
        Product product = productRepository.findById(wishedProductDTO.productId()).orElseThrow(NoSuchProductException::new);
        WishedProduct wishedProduct = new WishedProduct(memberDTO.toEntity(), product, wishedProductDTO.amount());
        return wishedProductRepository.save(wishedProduct).toDTO();
    }

    public WishedProductDTO deleteWishedProduct(long id) {
        WishedProductDTO deletedWishedProductDTO = wishedProductRepository.findById(id)
            .orElseThrow(NoSuchWishedProductException::new)
            .toDTO();
        wishedProductRepository.deleteById(id);
        return deletedWishedProductDTO;
    }

    public WishedProductDTO updateWishedProduct(WishedProductDTO wishedProductDTO) {
        long id = wishedProductDTO.id();
        int amount = wishedProductDTO.amount();
        if (amount == 0) {
            return deleteWishedProduct(id);
        }
        WishedProduct wishedProduct = wishedProductRepository.findById(id)
            .orElseThrow(NoSuchWishedProductException::new);
        wishedProduct.setAmount(amount);
        return wishedProductRepository.save(wishedProduct).toDTO();
    }
}
