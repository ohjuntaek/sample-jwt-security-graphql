package me.example.hellojsq.member;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;
import me.example.hellojsq.login.JWTUserDetails;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.google.common.base.Predicates.not;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class SecurityUserDetailService implements UserDetailsService {
    private static final String ADMIN_AUTHORITY = "ADMIN";
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTVerifier verifier;


    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Member member = memberRepository.findByLoginId(loginId).orElseThrow(RuntimeException::new);
        return User.builder()
                .username(member.getLoginId())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }


    public Member createMember(MemberRequest memberRequest) {
        memberRequest.password = passwordEncoder.encode(memberRequest.password);
        return memberRepository.save(Member.from(memberRequest));
    }

    @Transactional
    public JWTUserDetails loadUserByToken(String token) {
        JWTUserDetails jwtUserDetails = getDecodedToken(token)
                .map(DecodedJWT::getSubject)
                .flatMap(memberRepository::findByLoginId)
                .map(user -> getUserDetails(user, token))
                .orElseThrow(RuntimeException::new);
        return jwtUserDetails;
    }


    private JWTUserDetails getUserDetails(Member user, String token) {
        return JWTUserDetails
                .builder()
                .username(user.getLoginId())
                .password(user.getPassword())
                .authorities(Stream.of(user.getRole())
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList()))
                .token(token)
                .build();
    }

    private Optional<DecodedJWT> getDecodedToken(String token) {
        try {
            return Optional.of(verifier.verify(token));
        } catch(JWTVerificationException ex) {
            return Optional.empty();
        }
    }

    public boolean isAdmin() {
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        return authorities.stream().map(GrantedAuthority::getAuthority).anyMatch(ADMIN_AUTHORITY::equals);
    }

    public boolean isAuthenticated() {
        return Optional
                .ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .filter(Authentication::isAuthenticated)
                .filter(not(this::isAnonymous))
                .isPresent();
    }

    private boolean isAnonymous(Authentication authentication) {
        return authentication instanceof AnonymousAuthenticationToken;
    }
}
