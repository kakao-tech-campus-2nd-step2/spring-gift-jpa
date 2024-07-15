package gift.global.converter;

import gift.model.product.SearchType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class SearchTypeConverter implements Converter<String, SearchType> {

    @Override
    public SearchType convert(String source) {
        try {
            return SearchType.fromDescription(source);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid search type: " + source);
        }
    }
}
