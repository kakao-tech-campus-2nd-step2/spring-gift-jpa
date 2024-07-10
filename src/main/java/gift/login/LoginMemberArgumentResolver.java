package gift.login;

import gift.member.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class LoginMemberArgumentResolver implements HandlerMethodArgumentResolver {

    // JwtUitll 빈으로 등록하지 않기!!!!!!!!!!!!!!!!!!!!!
    private final MemberService memberService;
    //private final JwtTokenUtil jwtTokenUtil;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public LoginMemberArgumentResolver(MemberService memberService) {
        this.memberService = memberService;
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
        String userEmail = JwtTokenUtil.decodeJwt(token).getSubject();
        log.info("userEmail={}",userEmail);

        return memberService.getMemberByEmail(userEmail);
    }
}
