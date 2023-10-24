package com.example.safestop.TestCase

import com.example.safestop.model.Payment
import com.google.common.truth.Truth
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PaymentValidateTest{

    @Test
    fun isCartValid() {
        val balance = PaymentValidate.validPayment(Payment("20001","sudhais","10/24",781,0.0))
        assertFalse(balance)
    }

    @Test
    fun isDateValid() {
        val balance = PaymentValidate.validPayment(Payment("2000175000","sudhais","06/24",781,0.0))
        assertFalse(balance)
    }

    @Test
    fun isCvvValid() {
        val balance = PaymentValidate.validPayment(Payment("2000175000","sudhais","10/24",700,0.0))
        assertFalse(balance)
    }

    @Test
    fun isAllValid() {
        val balance = PaymentValidate.validPayment(Payment("2000175000","sudhais","10/24",781,0.0))
        assertTrue(balance)
    }

    @Test
    fun isWallet() {
        val balance = PaymentValidate.userWalletBalance("sudhais", Payment("2000175000","sudhais","10/24",781,1000.0))
        Truth.assertThat(balance).isEqualTo(2000.0)
    }
}