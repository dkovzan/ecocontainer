package com.econtainer.base.model;

import org.springframework.security.core.GrantedAuthority;

/**
 * Role assigned to user.
 *
 * @author DubininAY
 */
public enum Role implements GrantedAuthority {

    USER,

    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }

    @Override
    public String toString() {
        return name();
    }
}
