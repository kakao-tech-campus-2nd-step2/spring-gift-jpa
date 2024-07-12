package gift.config;

import gift.dto.PagingRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class PageRequestArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String DEFAULT_PAGE = "1";
    private static final String DEFAULT_SIZE = "5";

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(PagingRequest.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        int page = Integer.parseInt(webRequest.getParameter("page") != null ? webRequest.getParameter("page") : DEFAULT_PAGE);
        int size = Integer.parseInt(webRequest.getParameter("size") != null ? webRequest.getParameter("size") : DEFAULT_SIZE);
        return new PagingRequest(page, size);
    }
}
