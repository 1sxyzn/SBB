package com.mysite.sbb.user;

import lombok.Getter;

@Getter
public enum UserRole { // 인증 후 권한 관련, 상수 자료형 enum이므로 Setter없이 Getter만
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    UserRole(String value){
        this.value=value;
    }
    private String value;
}
