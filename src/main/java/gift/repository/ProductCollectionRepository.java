package gift.repository;

import gift.domain.Product;
import gift.exception.ErrorCode;
import gift.exception.NotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Deprecated(forRemoval = true)
public abstract class ProductCollectionRepository implements ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private final AtomicLong currentId = new AtomicLong(0L);

    @Override
    public Product getProductById(Long id) {
        var gift = products.get(id);
        if (gift == null) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return gift;
    }

    @Override
    public Long saveProduct(Product product) {
        Long id = currentId.getAndIncrement();
        products.put(id, product);
        return id;
    }

    @Override
    public Long updateProduct(Long id, Product product) {
        if (!products.containsKey(id)) {
            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        products.put(id, product);
        return id;
    }

//    @Override
//    public void deleteProductById(Long id) {
//        if (products.remove(id) == null) {
//            throw new NotFoundException(ErrorCode.PRODUCT_NOT_FOUND);
//        }
//    }
}
