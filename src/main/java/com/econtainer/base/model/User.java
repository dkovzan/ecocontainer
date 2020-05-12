package com.econtainer.base.model;

import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author DubininAY
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "roles")
    @Convert(converter = RolesToStringConverter.class)
    private Set<Role> roles;

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Converter
    private static class RolesToStringConverter implements AttributeConverter<Set<Role>, String> {

        private static final String DELIMITER = ",";

        @Override
        public String convertToDatabaseColumn(Set<Role> attribute) {
            return attribute.stream()
                .map(Role::name)
                .collect(Collectors.joining(DELIMITER));    }

        @Override
        public Set<Role> convertToEntityAttribute(String dbData) {

            return Arrays.stream(dbData.split(DELIMITER))
                .map(Role::valueOf)
                .collect(Collectors.toSet());
        }
    }
}
