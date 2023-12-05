package dev.vitorpaulo.amigosecreto.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfiguration {

    @Value("${jwt.secret:9834ujwe9ijdsvjn23r8uhhsaios123ej0i}")
    private String jwtSecret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtBuilder jwtBuilder() {
        return Jwts.builder()
                .signWith(
                        SignatureAlgorithm.HS512,
                        jwtSecret
                );
    }

    @Bean
    public JwtParser jwtParser() {
        return Jwts.parser()
                .setSigningKey(jwtSecret);
    }
}
