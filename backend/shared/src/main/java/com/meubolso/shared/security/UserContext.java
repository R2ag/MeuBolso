package com.meubolso.shared.security;

public class UserContext {

    private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

    public void setUserId(String userId) {
        USER_ID.set(userId);
    }

    public String getUserId() {
        return USER_ID.get();
    }

    public void clear() {
        USER_ID.remove();
    }
}
