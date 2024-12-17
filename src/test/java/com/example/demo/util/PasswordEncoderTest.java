package com.example.demo.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PasswordEncoderTest {


    @Test
    @DisplayName("암호화가 잘되는지")
    void encode() {
        // given
        String rawPassword = "1";

        // when
        String encode = PasswordEncoder.encode(rawPassword);

        // then
        assertNotEquals("1", encode);
    }

    @Test
    @DisplayName("복호화후 입력한 패스워드와 일치 하는지")
    void matches() {
        // given
        String rawPassword = "1";
        String encode = PasswordEncoder.encode(rawPassword);
        // when
        boolean matches = PasswordEncoder.matches("1", encode);
        // then
        assertTrue(matches);
    }
}