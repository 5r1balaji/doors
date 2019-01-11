package com.agam.doors.model;

import java.io.Serializable;


public class AuthToken  implements Serializable {

    private String token;
    private String username;

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                '}';
    }

    public AuthToken(){

    }

    public AuthToken(String token, String username){
        this.token = token;
        this.username = username;
    }

    public AuthToken(String token){
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
