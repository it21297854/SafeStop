package com.example.safestop.TestCase

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SignInValidateTest{

    @Test
    fun isPasswordEmpty() {

       val valid = SignInValidate.validateUser("check@gmail.com", "")
        assertFalse(valid)
    }

    @Test
    fun isEmailEmpty() {
        val valid = SignInValidate.validateUser("", "test")
        assertFalse(valid)
    }

    @Test
    fun isBothEmpty() {
        val valid = SignInValidate.validateUser("", "")
        assertFalse(valid)
    }

    @Test
    fun isBothWrong() {
        val valid = SignInValidate.validateUser("check", "1233")
        assertFalse(valid)
    }

    @Test
    fun isBothValid() {
        val valid = SignInValidate.validateUser("test@gmail.com", "test123")
        assertTrue(valid)
    }
}