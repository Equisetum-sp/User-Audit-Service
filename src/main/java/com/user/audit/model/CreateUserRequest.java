package com.user.audit.model;

public class CreateUserRequest {
    private User user;

    public CreateUserRequest(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
