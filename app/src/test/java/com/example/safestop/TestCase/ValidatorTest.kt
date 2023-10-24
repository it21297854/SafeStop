package com.example.safestop.TestCase

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
@RunWith(JUnit4::class)
class ValidatorTest{
    @Test
    fun whenInputIsValid(){
        val amount = 100
        val desc = "Some random desc"
        val result = Validator.validateInput(amount, desc)
        assertThat(result).isEqualTo(true)

    }
    @Test
    fun whenInputIsNotValid(){
        val amount = 0
        val desc = ""
        val result = Validator.validateInput(amount, desc)
        assertThat(result).isEqualTo(false)
    }


}
