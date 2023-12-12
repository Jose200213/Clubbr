package com.Clubbr.Clubbr.advice;

public class ManagerNotFoundException extends RuntimeException{
    public ManagerNotFoundException(String message) {
        super(message);
    }
}
