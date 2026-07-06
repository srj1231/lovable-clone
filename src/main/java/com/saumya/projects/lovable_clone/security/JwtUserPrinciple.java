package com.saumya.projects.lovable_clone.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.List;

/*
 * The authenticated user object representing "who" is making the request.
 */
public record JwtUserPrinciple(
        Long userId,
        String username,
        List<GrantedAuthority> authorities // permissions/roles granted to the user: "what" they can do.
        // E.g. "ROLE_USER", "ROLE_ADMIN", etc.
) {
}
