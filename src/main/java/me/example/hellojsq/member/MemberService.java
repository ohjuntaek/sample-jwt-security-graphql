package me.example.hellojsq.member;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import me.example.hellojsq.config.SecurityProperties;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class MemberService {
    private final MemberRepository memberRepository;
    private final SecurityUserDetailService userDetailsService;
    private final AuthenticationProvider authenticationProvider;
    private final SecurityProperties properties;
    private final Algorithm algorithm;

    @GraphQLQuery
    public MemberResponse getMemberInfo(String loginId) {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(RuntimeException::new);
        return MemberResponse.from(member);
    }

    @GraphQLMutation
    public MemberResponse createMember(MemberRequest memberRequest) {
        Member member = userDetailsService.createMember(memberRequest);
        return MemberResponse.from(member);
    }

    @GraphQLMutation
    @PreAuthorize("isAnonymous()")
    public LoginResponse login(String email, String password) {
        UsernamePasswordAuthenticationToken credentials = new UsernamePasswordAuthenticationToken(email, password);
        try {
            SecurityContextHolder.getContext().setAuthentication(authenticationProvider.authenticate(credentials));
            Member currentUser = getCurrentUser();
            return new LoginResponse(currentUser, getToken(currentUser));
        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(email);
        }
    }

    @Transactional
    public Member getCurrentUser() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getName)
                .flatMap(memberRepository::findByLoginId)
                .orElse(null);
    }

    @Transactional
    public String getToken(Member user) {
        Instant now = Instant.now();
        Instant expiry = Instant.now().plus(properties.getTokenExpiration());
        return JWT
                .create()
                .withIssuer(properties.getTokenIssuer())
                .withIssuedAt(Date.from(now))
                .withExpiresAt(Date.from(expiry))
                .withSubject(user.getLoginId())
                .sign(algorithm);
    }


}
