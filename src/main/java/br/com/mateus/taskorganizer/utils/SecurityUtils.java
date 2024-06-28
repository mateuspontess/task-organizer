package br.com.mateus.taskorganizer.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;

public class SecurityUtils {

    private SecurityUtils() {}

    private static UserEntity getUserDetails() {
        return (UserEntity) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }

    public static Long getUserId() {
        UserEntity entity = getUserEntity();
        return entity.getId();
    }

    public static UserEntity getUserEntity() {
        return (UserEntity) getUserDetails();
    }
}