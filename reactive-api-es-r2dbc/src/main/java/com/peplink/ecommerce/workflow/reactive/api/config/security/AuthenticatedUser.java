/**
 * Copyright (c) 2020 Peplink, Inc. All Rights Reserved.
 *
 * This software is the proprietary information of Peplink, Inc.
 * Use is subject to license terms.
 */
package com.peplink.ecommerce.workflow.reactive.api.config.security;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.CollectionUtils;

import lombok.Data;

/**
 * The DTO class {@link AuthenticatedUser}
 * 
 * @author Peplink, Inc.
 */
@Data
public class AuthenticatedUser implements Serializable {

    private static final long serialVersionUID = 1L;

    private UUID id;
    private String email;
    private String password;
    private Set<String> roles;
    private Date tokenCreationDate;
    private String bearerToken;

    public AuthenticatedUser() {
    }

    public List<GrantedAuthority> getGrantedAuths() {
        if (CollectionUtils.isEmpty(this.roles)) {
            return null;
        }
        return this.roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
    }

}
