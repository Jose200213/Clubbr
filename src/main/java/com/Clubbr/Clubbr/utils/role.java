package com.Clubbr.Clubbr.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum role {
    ADMIN(Arrays.asList(permission.READ_ALL, permission.WRITE_ALL, permission.READ_STABLISHMENTS)),
    USER(Arrays.asList(permission.READ_STABLISHMENTS)),
    WORKER(Arrays.asList(permission.READ_STABLISHMENTS));

    private List<permission> permissions;

    public void setPermissions(List<permission> permissions) {
        this.permissions = permissions;
    }
}
