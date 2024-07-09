package gift.service;

import gift.dto.WishedProductDTO;
import gift.DAO.WishedProductDAO;
import gift.exception.NoSuchProductException;
import gift.repository.ProductRepository;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishedProductService {

    private final WishedProductDAO wishedProductDAO;
    private final ProductRepository productRepository;

    @Autowired
    public WishedProductService(WishedProductDAO wishedProductDAO, ProductRepository productDAO) {
        this.wishedProductDAO = wishedProductDAO;
        this.productRepository = productDAO;
    }

    public Collection<WishedProductDTO> getWishedProducts(String memberEmail) {
        return wishedProductDAO.getWishedProducts(memberEmail);
    }

    public WishedProductDTO addWishedProduct(String memberEmail, WishedProductDTO wishedProductDTO) {
        productRepository.findById(wishedProductDTO.productId())
            .orElseThrow(NoSuchProductException::new);
        WishedProductDTO addedWishedProductDTO = new WishedProductDTO(memberEmail, wishedProductDTO.productId(), wishedProductDTO.amount());
        wishedProductDAO.addWishedProduct(addedWishedProductDTO);
        return addedWishedProductDTO;
    }

    public WishedProductDTO deleteWishedProduct(String memberEmail, WishedProductDTO wishedProductDTO) {
        productRepository.findById(wishedProductDTO.productId())
            .orElseThrow(NoSuchProductException::new);
        WishedProductDTO deletedWishedProductDTO = new WishedProductDTO(memberEmail, wishedProductDTO.productId(), wishedProductDTO.amount());
        wishedProductDAO.deleteWishedProduct(deletedWishedProductDTO);
        return deletedWishedProductDTO;
    }

    public WishedProductDTO updateWishedProduct(String memberEmail, WishedProductDTO wishedProductDTO) {
        productRepository.findById(wishedProductDTO.productId())
            .orElseThrow(NoSuchProductException::new);
        if (wishedProductDTO.amount() == 0) {
            return deleteWishedProduct(memberEmail, wishedProductDTO);
        }
        WishedProductDTO updatedWishedProductDTO = new WishedProductDTO(memberEmail, wishedProductDTO.productId(), wishedProductDTO.amount());
        wishedProductDAO.updateWishedProduct(updatedWishedProductDTO);
        return updatedWishedProductDTO;
    }
}
