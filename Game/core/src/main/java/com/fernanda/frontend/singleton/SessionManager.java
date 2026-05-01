package com.fernanda.frontend.singleton;

public class SessionManager {
    private static SessionManager instance;

    private int accountId;
    private String username;
    private boolean isLoggedIn = false;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setSession(int accountId, String username) {
        this.accountId = accountId;
        this.username = username;
        this.isLoggedIn = true;
    }

    public int getAccountId() { return accountId; }
    public String getUsername() { return username; }
    public boolean isLoggedIn() { return isLoggedIn; }
}
