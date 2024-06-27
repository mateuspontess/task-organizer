package br.com.mateus.taskorganizer.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.mateus.taskorganizer.infra.persistence.user.UserEntity;

public class SecurityUtils {

    private SecurityUtils() {}

    public static UserEntity getUser() {
        return (UserEntity) SecurityContextHolder
            .getContext()
            .getAuthentication()
            .getPrincipal();
    }
    public static Long getUserId() {
        UserEntity entity = getUser();
        return entity.getId();
    }
}