package gift.feat.product.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gift.feat.product.domain.Product;
import gift.feat.product.domain.SearchType;
import gift.feat.product.contoller.dto.ProductRequestDto;
import gift.core.exception.product.DuplicateProductIdException;
import gift.core.exception.product.ProductNotFoundException;
import gift.feat.product.contoller.dto.ProductResponseDto;
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

	@Transactional(readOnly = true)
	public Page<Product> getProductsWithPaging(Pageable pageable, SearchType searchType, String searchValue) {
		if (searchValue == null || searchValue.isBlank()) {
			return productRepository.findAll(pageable);
		}
		return switch (searchType) {
			case NAME -> productRepository.findByNameContaining(searchValue, pageable);
		};
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

	@Transactional
	public void deleteProduct(Long id) {
		Product existingProduct = productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
		productRepository.deleteById(id);
	}
}