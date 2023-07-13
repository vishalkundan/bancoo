package com.kanna.banco.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.kanna.banco.entity.Permission.*;

@RequiredArgsConstructor
public enum Role {
    USER(Collections.emptySet()),
    MANAGER(
            Set.of(
                    MANAGER_READ,
                    MANAGER_UPDATE,
                    MANAGER_CREATE
            )
    );

    @Getter
    private final Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getAuthorities() {
        var authorities = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getConsent()))
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
