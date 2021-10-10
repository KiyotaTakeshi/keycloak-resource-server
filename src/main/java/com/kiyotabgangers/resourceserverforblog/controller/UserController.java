package com.kiyotabgangers.resourceserverforblog.controller;

import com.kiyotabgangers.resourceserverforblog.entity.User;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

/**
 * @author KIYOTA, Takeshi
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/status")
    public String status() {
        return "working...";
    }

    // @Secured("ROLE_developer")
    // @Secured("ROLE_admin")
    @PreAuthorize("hasRole('developer') or #id == #jwt.subject")
    // @PreAuthorize("#id == #jwt.subject")
    // @PreAuthorize("hasRole('admin')")
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return "Deleted user: " + id + " and JWT subject " + jwt.getSubject();
    }

    @PostAuthorize("returnObject.id == #jwt.subject")
    @GetMapping("/{id}")
    public User getUser(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        return new User("e1a0f390-33dc-43c9-853e-429504cfca8a", "mike", "popcorn");
    }
}
