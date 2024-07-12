package gift.feat.wishProduct.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gift.feat.product.domain.Product;
import gift.feat.product.repository.ProductJpaRepository;
import gift.feat.user.User;
import gift.feat.user.repository.UserJpaRepository;
import gift.feat.wishProduct.controller.Dto.WishProductResponseDto;
import gift.feat.wishProduct.domain.WishProduct;
import gift.feat.wishProduct.repository.WishProductJpaRepository;

@Service
public class WishProductService {
	private final WishProductJpaRepository wishProductJpaRepository;
	private final ProductJpaRepository productJpaRepository;
	private final UserJpaRepository userJpaRepository;

	@Autowired
	public WishProductService(WishProductJpaRepository wishProductJpaRepository,
		ProductJpaRepository productJpaRepository,
		UserJpaRepository userJpaRepository) {
		this.wishProductJpaRepository = wishProductJpaRepository;
		this.productJpaRepository = productJpaRepository;
		this.userJpaRepository = userJpaRepository;
	}


	@Transactional(readOnly = true)
	public List<WishProductResponseDto> getByUserId(Long userId) {
		return wishProductJpaRepository.findByUserId(userId).stream()
			.map(WishProductResponseDto::from)
			.toList();
	}

	@Transactional(readOnly = true)
	public Page<WishProductResponseDto> getByUserId(Long userId, Pageable pageable) {
		return wishProductJpaRepository.findByUserId(userId, pageable)
			.map(WishProductResponseDto::from);
	}

	@Transactional
	public WishProduct save(Long productId, Long userId) {
		User user = userJpaRepository.findById(userId).orElseThrow();
		Product product = productJpaRepository.findById(productId).orElseThrow();
		WishProduct wishProduct = WishProduct.of(user, product);
		return wishProductJpaRepository.save(wishProduct);
	}

	@Transactional
	public void delete(WishProduct wishProduct) {
		wishProductJpaRepository.delete(wishProduct);
	}
}
