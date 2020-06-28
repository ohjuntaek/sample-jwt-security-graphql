package me.example.hellojsq.config;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.time.Duration;

@ConstructorBinding
@ConfigurationProperties(prefix = "hellojsq.security")
@Getter
@RequiredArgsConstructor
public class SecurityProperties {
    /**
     * Amound of hashing iterations, where formula is 2^passwordStrength iterations
     */
    private final int passwordStrength;
    /**
     * Secret used to generate and verify JWT tokens
     */
    private final String tokenSecret = "hellojsq";
    /**
     * Name of the token issuer
     */
    private final String tokenIssuer = "hellojsq";
    /**
     * Duration after which a token will expire
     */
    private final Duration tokenExpiration = Duration.ofHours(4);
}
