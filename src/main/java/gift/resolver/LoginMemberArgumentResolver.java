package gift.resolver;

import gift.model.Member;
import gift.model.dto.LoginMemberDto;
import gift.repository.MemberDao;
import gift.resolver.annotation.LoginMember;
import gift.service.AuthService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberDao memberDao;
    private final AuthService authService;

    public LoginMemberArgumentResolver(MemberDao memberDao, AuthService authService) {
        this.memberDao = memberDao;
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterAnnotation(LoginMember.class) != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String token = authService.extractToken(webRequest.getHeader("Authorization"));
        Long id = Long.parseLong(authService.getClaims(token).getSubject());
        Member member = memberDao.selectMemberById(id);
        return new LoginMemberDto(member.getId(), member.getName(), member.getEmail(), member.getRole());
    }
}
