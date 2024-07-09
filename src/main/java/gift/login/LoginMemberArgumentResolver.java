package gift.login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import gift.member.MemberDao;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    private final MemberDao memberDao;
    private final JwtTokenUtil jwtTokenUtil;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LoginMemberArgumentResolver(MemberDao memberDao, JwtTokenUtil jwtTokenUtil) {
        this.memberDao = memberDao;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hashLoginUserAnnotation = parameter.hasParameterAnnotation(LoginMember.class);
        return hashLoginUserAnnotation;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String token = webRequest.getHeader("Authorization").substring(7);
        log.info("token={}",token);
        String userEmail = jwtTokenUtil.decodeJwt(token).getSubject();
        log.info("userEmail={}",userEmail);

        return memberDao.findMemberById(userEmail);
    }
}
