package gift.feat.wishList;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gift.feat.product.domain.Product;
import gift.feat.product.repository.ProductJpaRepository;
import gift.feat.user.User;
import gift.feat.user.repository.UserJpaRepository;

@Service
public class WishProductService {
	private final WishProductJpaRepository wishProductJpaRepository;
	private final ProductJpaRepository productJpaRepository;
	private final UserJpaRepository userJpaRepository;


	public List<WishProduct> findByUserId(Long userId) {
		return wishProductJpaRepository.findByUserId(userId);
	}

	public WishProduct save(Long productId, Long userId) {
		User user = userJpaRepository.findById(userId).orElseThrow();
		Product product = productJpaRepository.findById(productId).orElseThrow();
		WishProduct wishProduct = WishProduct.of(user, product);
		return wishProductJpaRepository.save(wishProduct);
	}

	public void delete(WishProduct wishProduct) {
		wishProductJpaRepository.delete(wishProduct);
	}

	@Autowired
	public WishProductService(WishProductJpaRepository wishProductJpaRepository,
		ProductJpaRepository productJpaRepository,
		UserJpaRepository userJpaRepository) {
		this.wishProductJpaRepository = wishProductJpaRepository;
		this.productJpaRepository = productJpaRepository;
		this.userJpaRepository = userJpaRepository;
	}
}
