package com.raf.airportuserservice.security;

public class SecurityConstants {
    public static final String SECRET = "mySecretKey";

    public static final long TOKEN_EXPIRATION_TIME = 86400000;
    public static final String TOKEN_PREFIX = "Basic ";
    public static final String HEADER_STRING = "Authorization";


    public static final String LOGIN_PATH = "/login";
    public static final String REGISTRATION_PATH = "/register";
    public static final String VERIFICATION_PATH = "/confirm-account";
}
