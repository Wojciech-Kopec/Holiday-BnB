package com.kopec.wojciech.engineers_thesis.security;

import org.springframework.stereotype.Component;

@Component
public class Roles {

    public final String ADMIN = "ROLE_ADMIN";
    public final String OWNER = "ROLE_OWNER";
    public final String USER = "ROLE_USER";
}
