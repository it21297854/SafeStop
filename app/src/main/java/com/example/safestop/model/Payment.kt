package com.example.safestop.model

//setting the payment details model as the data class
data class Payment(
//    var payID:String? = null,
//    var type:String? = null,
    var cardNo:String? = null,
    var name:String? = null,
    var expDate:String? = null,
    var cvv:Int? = null,
    var balance:Double? = null
)
