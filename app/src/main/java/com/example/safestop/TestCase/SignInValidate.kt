package com.example.safestop.TestCase

object SignInValidate {

    private val email:String = "test@gmail.com"
    private val password:String = "test123"

    fun validateUser(email:String, password:String):Boolean{
        if(email.isEmpty() || password.isEmpty())
            return false

        if(email.equals(this.email) && password.equals(this.password))
            return true
        return false
    }

}