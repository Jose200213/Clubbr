package com.Clubbr.Clubbr.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum attendance {
    FALSE(0),
    TRUE(1),
    PENDING(2);

    private final int value;
}
