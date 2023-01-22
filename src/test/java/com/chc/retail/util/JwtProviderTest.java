package com.chc.retail.util;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

class JwtProviderTest {
    @Test
    void giveTokenWhenValidatorValidates() {
        assertFalse((new JwtProvider()).validateToken("ABC123"));
    }
}

