package com.example.safestop.TestCase

import com.example.safestop.model.Payment
import com.example.safestop.model.User

object PaymentValidate {

    private val payment = Payment("2000175000", "sudhais", "10/24", 781, 10000.0)
    private val user = User("sudhais", 1000.0)

    fun validPayment(payment: Payment): Boolean {
        return payment.cardNo.equals(this.payment.cardNo) &&
                payment.expDate.equals(this.payment.expDate) &&
                payment.cvv!!.equals(this.payment.cvv)
    }

    fun userWalletBalance(user:String, payment: Payment):Double {
        val valid = this.validPayment(payment)
        if(valid && this.payment.balance!! >= payment.balance!!){
            this.user.balance = this.user.balance?.plus(payment.balance!!)
        }
        return this.user.balance!!
    }
}