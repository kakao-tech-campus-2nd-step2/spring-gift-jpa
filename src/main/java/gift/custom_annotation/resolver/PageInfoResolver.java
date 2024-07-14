package gift.custom_annotation.resolver;

import gift.custom_annotation.annotation.PageInfo;
import gift.util.pagenation.PageInfoDTO;
import gift.exception.InvalidPageRequestException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class PageInfoResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(PageInfo.class);
    }

    @Override
    public PageInfoDTO resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        PageInfoDTO pageInfoDTO;

        try {
            pageInfoDTO = new PageInfoDTO(
                    extractPageNum(webRequest),
                    extractPageSize(webRequest),
                    extractSortType(webRequest, parameter),
                    extractSortOrder(webRequest)
            );
        } catch (NoSuchFieldException e) {
            throw new InvalidPageRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InvalidPageRequestException();
        }

        return pageInfoDTO;
    }

    private int extractPageNum(NativeWebRequest webRequest) {
        String pageParam = webRequest.getParameter("page");

        if (pageParam == null) {
            return 0;
        }

        return Integer.parseInt(pageParam);
    }

    private int extractPageSize(NativeWebRequest webRequest) {
        String pageSizeParam = webRequest.getParameter("size");

        if (pageSizeParam == null) {
            return 10;
        }

        return Integer.parseInt(pageSizeParam);
    }

    private Boolean extractSortOrder(NativeWebRequest webRequest) {
        String ascParam = webRequest.getParameter("asc");

        if (ascParam == null) {
            return true;
        }

        return Boolean.parseBoolean(ascParam);
    }

    private String extractSortType(NativeWebRequest webRequest, MethodParameter parameter) throws NoSuchFieldException {
        String sortParam = webRequest.getParameter("sort");

        if (sortParam == null) {
            return "id";
        }

        Class<?> entityClass = parameter.getParameterAnnotation(PageInfo.class).entityClass();
        List<String> fields = getFieldNames(entityClass);

        if (!fields.contains(sortParam)) {
            throw new NoSuchFieldException("No such field corresponding to this sort type : " + sortParam);
        }

        return sortParam;
    }

    private List<String> getFieldNames(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        return Arrays.stream(fields)
                .map((field) -> field.getName())
                .toList();
    }
}
