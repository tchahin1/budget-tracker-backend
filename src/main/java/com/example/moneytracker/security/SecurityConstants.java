package com.example.moneytracker.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME = 64_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users/register";
    public static final String CATEGORY_EXPENSE = "/expenses/category";
    public static final String EXPENSE_DELETE = "/expenses/delete";
    public static final String EXPENSE_ADD = "/expenses/add";
    public static final String REMINDERS_GET = "/reminders/get";
}
