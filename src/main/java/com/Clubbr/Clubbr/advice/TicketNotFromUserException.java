package com.Clubbr.Clubbr.advice;

public class TicketNotFromUserException extends RuntimeException {
    public TicketNotFromUserException(String message) {
        super(message);
    }
}
