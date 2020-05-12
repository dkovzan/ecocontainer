package com.econtainer.base.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author DubininAY
 */
public enum Role implements GrantedAuthority {

    USER,

    ADMIN;

    private static final String ROLE_PREFIX = "ROLE_";

    @Override
    public String getAuthority() {
        return ROLE_PREFIX + name();
    }

    @Override
    public String toString() {
        return name();
    }
}
