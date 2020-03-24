/**
 * Copyright (c) 2020 Peplink, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Peplink, Inc.
 * Use is subject to license terms.
 */
package com.peplink.ecommerce.workflow.reactive.api.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;

import com.peplink.ecommerce.workflow.reactive.api.config.security.AuthenticatedUser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

/**
 * The Utility class {@link AccessUtililty}
 */
public class AccessUtililty {

    private static final Logger _LOGGER = LoggerFactory.getLogger(AccessUtililty.class);

    public static AuthenticatedUser getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null
                && !authentication.getPrincipal().equals("anonymousUser")) {
            AuthenticatedUser authenticatedUser = (AuthenticatedUser) authentication.getPrincipal();
            return authenticatedUser;
        }
        return null;
    }

    public static AuthenticatedUser getAuthenticatedUser(Authentication authentication) {
        if (authentication != null) {
            return (AuthenticatedUser) authentication.getPrincipal();
        }
        return null;
    }

    public static String parseTokenFromSecretKey(String token, String secretKey) {
        return parseTokenFromSecretKey(token, secretKey, false);
    }

    public static String parseTokenFromSecretKey(String token, String secretKey, boolean isRefreshToken) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(secretKey)) {
            return null;
        }
        try {
            return Jwts.parser().setSigningKey(secretKey.getBytes())
                    .parseClaimsJws(extractBearerToken(token)).getBody().getSubject();
        } catch (SignatureException e) {
            _LOGGER.debug("Security secret key did not work, try to parse using client secret key.");
        } catch (ExpiredJwtException e) {
            if (isRefreshToken) {
                return e.getClaims().getSubject();
            }
            _LOGGER.debug("Access token expired.");
        }
        return null;
    }

    public static String extractBearerToken(String bearerToken) {
        if (StringUtils.isEmpty(bearerToken)) {
            return null;
        }
        return bearerToken.replace(Constants.TOKEN_PREFIX, "");
    }
}
