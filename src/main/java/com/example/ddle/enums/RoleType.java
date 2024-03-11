package com.example.ddle.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleType {
    ADMIN("ROLE_ADMIN","관리자"),
    USER("ROLE_USER","일반 사용자");

    private final String key;
    private final String title;
}
