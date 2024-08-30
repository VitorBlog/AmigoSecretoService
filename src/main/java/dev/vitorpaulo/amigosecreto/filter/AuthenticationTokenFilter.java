package dev.vitorpaulo.amigosecreto.filter;

import dev.vitorpaulo.amigosecreto.exception.CannotAuthenticateException;
import dev.vitorpaulo.amigosecreto.service.UserService;
import io.jsonwebtoken.JwtParser;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    private static final String PREFIX = "Bearer ";
    public static final String AUTHORIZATION = "Authorization";
    private static final List<AntPathRequestMatcher> PUBLIC_URLS = List.of(
            new AntPathRequestMatcher("/auth/v1/**"),
            new AntPathRequestMatcher("/docs"),
            new AntPathRequestMatcher("/swagger-ui/**"),
            new AntPathRequestMatcher("/v3/api-docs/**")
    );

    private final JwtParser jwtParser;

    private final UserService userService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getMethod().equalsIgnoreCase("OPTIONS")
                || PUBLIC_URLS.stream().anyMatch(value -> value.matches(request));
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            var header = request.getHeader(AUTHORIZATION);

            if (header != null && header.startsWith(PREFIX)) {
                final var authorization = header.replace(PREFIX, "");
                if (!jwtParser.isSigned(authorization)) {
                    throw new CannotAuthenticateException();
                }

                final var token = jwtParser.parseClaimsJws(authorization);
                final var user = userService.findUserById(Long.parseLong(token.getBody().getSubject()));
                final var authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        Collections.emptyList()
                );

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);

                filterChain.doFilter(request, response);
                return;
            }
        } catch (Exception exception) {
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
            log.error("Unable to authenticate the request. {}", exception);
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }
}
