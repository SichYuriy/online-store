package com.gmail.at.sichyuriyy.onlinestore.security;

/**
 * Created by Yuriy on 4/6/2017.
 */
public class AccessDeniedException extends RuntimeException {

    public AccessDeniedException() {
        super("Access denied!");
    }
}
