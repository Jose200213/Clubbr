package com.Clubbr.Clubbr.advice;

public class ManagerNotOwnerException extends RuntimeException {
    public ManagerNotOwnerException(String message) {
        super(message);
    }
}
