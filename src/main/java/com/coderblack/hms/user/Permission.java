package com.coderblack.hms.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ("admin:read"),
    ADMIN_UPDATE("admin:update"),
    ADMIN_WRITE("admin:write"),
    ADMIN_DELETE("admin:delete"),

    STAFF_READ("staff:read"),
    STAFF_UPDATE("staff:update"),
    STAFF_WRITE("staff:write"),
    STAFF_DELETE("staff:delete");

    @Getter
    private final String permission;
}
