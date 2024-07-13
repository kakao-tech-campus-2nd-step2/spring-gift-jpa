package gift.global.component;

import gift.global.annotation.Products;
import gift.product.dto.ProductResponseDto;
import gift.product.service.ProductService;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

// 사용자에게 보여줄 제품 목록을 처리하는 resolver
@Component
public class ProductsResolver implements HandlerMethodArgumentResolver {

    private final ProductService productService;

    @Autowired
    public ProductsResolver(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 어노테이션이 붙었는지 확인
        boolean isTokenAnnotation = parameter.hasParameterAnnotation(Products.class);
        // 리스트 여부 확인
        boolean isList = parameter.getParameterType().equals(List.class);
        // 리스트의 제네릭 확인
        ParameterizedType parameterizedType = (ParameterizedType) parameter.getGenericParameterType();
        boolean isProductList = parameterizedType.getActualTypeArguments()[0].equals(
            ProductResponseDto.class);

        return isTokenAnnotation && isList && isProductList;
    }

    // 모든 제품 목록 가져 옴.
    @Override
    public List<ProductResponseDto> resolveArgument(MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        List<ProductResponseDto> products = productService.selectProducts();

        return products;
    }
}
