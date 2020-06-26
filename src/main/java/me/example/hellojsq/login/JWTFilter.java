package me.example.hellojsq.login;

import lombok.RequiredArgsConstructor;
import me.example.hellojsq.member.SecurityUserDetailService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.google.common.base.Predicates.not;

@Component
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final Pattern BEARER_PATTERN = Pattern.compile("^Bearer (.+?)$");
    private final SecurityUserDetailService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        Optional<JWTUserDetails> jwtUserDetails = getToken(request)
                .map(userService::loadUserByToken);
        Optional<JWTPreAuthenticationToken> jwtPreAuthenticationToken = jwtUserDetails
                .map(userDetails -> JWTPreAuthenticationToken
                        .builder()
                        .principal(userDetails)
                        .details(new WebAuthenticationDetailsSource().buildDetails(request))
                        .build());
        System.out.println("ㅅㅂ");
        jwtPreAuthenticationToken
                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));
        filterChain.doFilter(request, response);

    }

    private Optional<String> getToken(HttpServletRequest request) {
        Optional<String> header = Optional
                .ofNullable(request.getHeader(AUTHORIZATION_HEADER));
        Optional<String> s = header
                .filter(not(String::isEmpty));
        Optional<Matcher> matcher1 = s
                .map((e) -> BEARER_PATTERN.matcher(e));
        return matcher1
                .filter(Matcher::find)
                .map(matcher -> matcher.group(1));
    }
}