package gift.service;

import gift.common.exception.DuplicateDataException;
import gift.common.exception.EntityNotFoundException;
import gift.controller.dto.request.WishInsertRequest;
import gift.controller.dto.request.WishPatchRequest;
import gift.controller.dto.response.WishResponse;
import gift.repository.ProductRepository;
import gift.repository.WishDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishService {
    private final WishDao wishDao;
    private final ProductRepository productRepository;

    public WishService(WishDao wishDao, ProductRepository productRepository) {
        this.wishDao = wishDao;
        this.productRepository = productRepository;
    }

    public void update(WishPatchRequest request, Long memberId) {
        checkProductExist(request.productId(), memberId);
        if (request.productCount() == 0) {
            deleteByProductId(request.productId(), memberId);
            return;
        }
        wishDao.update(request, memberId);
    }

    public void save(WishInsertRequest request, Long memberId) {
        checkProductExist(request.productId());
        checkDuplicateWish(request.productId(), memberId);
        wishDao.save(request, memberId);
    }

    public List<WishResponse> findAllByMemberId(Long memberId) {
        return wishDao.findAllByMemberId(memberId).stream()
                .map(WishResponse::from)
                .toList();
    }

    public void deleteByProductId(Long productId, Long memberId) {
        wishDao.deleteByProductId(productId, memberId);
    }

    private void checkProductExist(Long productId, Long memberId) {
        if (!wishDao.existsByProductIdAndMemberId(productId, memberId)) {
            throw new EntityNotFoundException("Product with id " + productId + " does not exist in wish");
        }
    }

    private void checkProductExist(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new EntityNotFoundException("Product with id " + productId + " does not exist");
        }
    }

    private void checkDuplicateWish(Long productId, Long memberId) {
        if (wishDao.existsByProductIdAndMemberId(productId, memberId)) {
            throw new DuplicateDataException("Product with id " + productId + " already exists in wish", "Duplicate Wish");
        }
    }
}
