package gift.feat.product.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gift.feat.product.domain.Product;
import gift.feat.product.dto.ProductRequestDto;
import gift.core.exception.product.DuplicateProductIdException;
import gift.core.exception.product.ProductNotFoundException;
import gift.feat.product.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductJpaRepository productRepository;

	@Transactional
	public Long saveProduct(ProductRequestDto productRequestDto) {
		if (productRepository.findByName(productRequestDto.name()) != null) {
			throw new DuplicateProductIdException(productRequestDto.name());
		}
		return productRepository.save(productRequestDto.toEntity()).getId();
	}

	@Transactional(readOnly = true)
	public Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
	}

	@Transactional(readOnly = true)
	public List<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Transactional
	public Long updateProduct(Long id, ProductRequestDto productRequestDto) {
		Product existingProduct = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		existingProduct.setName(productRequestDto.name());
		existingProduct.setPrice(productRequestDto.price());
		existingProduct.setImageUrl(productRequestDto.imageUrl());
		return productRepository.save(existingProduct).getId();
	}

	public void deleteProduct(Long id) {
		Product existingProduct = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		productRepository.deleteById(id);
	}
}