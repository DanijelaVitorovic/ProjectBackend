package com.dex.coreserver.security;

public class SecurityConstants {
    public static final String SIGN_UP_URSL = "/api/user/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Core ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 3_000_000;
}
