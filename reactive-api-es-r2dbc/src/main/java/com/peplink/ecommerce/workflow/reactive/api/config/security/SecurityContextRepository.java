package com.peplink.ecommerce.workflow.reactive.api.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ServerWebExchange;

import com.peplink.ecommerce.workflow.reactive.api.exception.AuthenticationException;
import com.peplink.ecommerce.workflow.reactive.api.util.AccessUtililty;
import com.peplink.ecommerce.workflow.reactive.api.util.JsonUtility;

import reactor.core.publisher.Mono;

@Component
public class SecurityContextRepository implements ServerSecurityContextRepository {

    private static final Logger logger = LoggerFactory.getLogger(SecurityContextRepository.class);

    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    public Mono<Void> save(ServerWebExchange swe, SecurityContext sc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange swe) {
        ServerHttpRequest request = swe.getRequest();
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        String token = null;
        if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
            token = authHeader.replace(TOKEN_PREFIX, "");
        } else {
            logger.warn("couldn't find bearer string, will ignore the header.");
        }

        String parsedTokenSecretKey = AccessUtililty.parseTokenFromSecretKey(token, "SecretKeyToGenJWTs");

        AuthenticatedUser authenticatedUser = (AuthenticatedUser) JsonUtility.jsonStringToObject(parsedTokenSecretKey,
                AuthenticatedUser.class);
        if (ObjectUtils.isEmpty(authenticatedUser)) {
            logger.error("Error parsing Authenticated User");
            throw new AuthenticationException("Invalid Token");
        }
        authenticatedUser.setBearerToken(AccessUtililty.extractBearerToken(token));

        if (token != null) {
            Authentication auth = new UsernamePasswordAuthenticationToken(authenticatedUser, null,
                    authenticatedUser.getGrantedAuths());
            SecurityContextHolder.getContext().setAuthentication(auth);
            return Mono.just(new SecurityContextImpl(auth));
        } else {
            return Mono.empty();
        }
    }

}