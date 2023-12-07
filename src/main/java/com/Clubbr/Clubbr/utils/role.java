package com.Clubbr.Clubbr.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum role {
    ADMIN(Arrays.asList(permission.READ_STABLISHMENTS, permission.CREATE_EVENTS,
            permission.CREATE_OWNER_MANAGER,
            permission.READ_MY_USER,
            permission.CREATE_INTEREST_POINTS, permission.CREATE_STABLISHMENTS,
            permission.CREATE_TICKETS, permission.READ_EVENTS, permission.READ_INTEREST_POINTS,
            permission.READ_TICKETS, permission.READ_USERS, permission.UPDATE_USERS,
            permission.DELETE_USERS, permission.UPDATE_STABLISHMENTS, permission.DELETE_STABLISHMENTS,
            permission.UPDATE_EVENTS, permission.DELETE_EVENTS, permission.UPDATE_INTEREST_POINTS,
            permission.DELETE_INTEREST_POINTS)),

    MANAGER(Arrays.asList(permission.READ_STABLISHMENTS, permission.CREATE_EVENTS,
            permission.CREATE_INTEREST_POINTS, permission.CREATE_STABLISHMENTS,
            permission.CREATE_STAB_MANAGERS,
            permission.READ_MY_USER,
            permission.READ_MANAGER_STABLISHMENTS, permission.CREATE_WORKERS,
            permission.CREATE_ITEMS, permission.UPDATE_ITEMS, permission.DELETE_ITEMS,
            permission.READ_ITEMS,
            permission.DELETE_STABLISHMENT_WORKERS,
            permission.UPDATE_WORKERS,
            permission.CREATE_TICKETS, permission.READ_EVENTS, permission.READ_INTEREST_POINTS,
            permission.READ_TICKETS, permission.UPDATE_USERS,
            permission.DELETE_USERS, permission.UPDATE_STABLISHMENTS, permission.DELETE_STABLISHMENTS,
            permission.UPDATE_EVENTS, permission.DELETE_EVENTS, permission.UPDATE_INTEREST_POINTS,
            permission.DELETE_INTEREST_POINTS)),

    USER(Arrays.asList(permission.READ_STABLISHMENTS, permission.READ_EVENTS,
            permission.READ_INTEREST_POINTS, permission.READ_TICKETS, permission.READ_MY_USER,
            permission.UPDATE_USERS, permission.DELETE_USERS)),

    WORKER(Arrays.asList(permission.READ_STABLISHMENTS, permission.READ_EVENTS,
            permission.READ_INTEREST_POINTS, permission.READ_TICKETS, permission.READ_MY_USER,
            permission.READ_ITEMS,
            permission.UPDATE_USERS, permission.DELETE_USERS));

    private List<permission> permissions;

    public void setPermissions(List<permission> permissions) {
        this.permissions = permissions;
    }
}
