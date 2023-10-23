package com.example.safestop.database

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.safestop.activity.MainActivity
import com.example.safestop.model.Payment
import com.example.safestop.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper {
    //using singleton pattern gettting database reference
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference


    //create a user when sig up the application
    fun createUser(user: User, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {

        val taskRef = databaseReference.child("User").child(user.userID!!)
        taskRef.setValue(user)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    //creating a payment data
    fun createPayment(payment: Payment, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {

        val taskRef = databaseReference.child("payment").child(payment.cardNo!!)
        taskRef.setValue(payment)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    //update the balance after credited successfully
    fun updateWalletBalance(userID:String, balance: Double, onSuccess: () -> Unit) {

        this.getSingleUserData(userID){

            if(it != null){
                var newBalance = User(userID,balance + it!!)
                databaseReference.child("User").child(userID!!).setValue(newBalance)
                    .addOnSuccessListener {
                        onSuccess()
                    }
            }

        }
    }

    //update payment balance after credited successfully
    fun updatePaymentBalance(balance:Double, payment: Payment) {
        var newBalance = Payment(payment.cardNo,payment.name,payment.expDate,payment.cvv,payment.balance!! - balance)
        databaseReference.child("payment").child(payment.cardNo!!).setValue(newBalance)

    }

    //getting single payment data passing the cartNumber
    fun getSinglePaymentData(id:String, callback: (Payment?) -> Unit){
        var ref = databaseReference.child("payment").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                   val paycheck = dataSnapshot.getValue(Payment::class.java)
                    val cartNo = dataSnapshot.child("cardNo").value.toString()
                    val cvv = dataSnapshot.child("cvv").value.toString().toInt()
                    val expDate = dataSnapshot.child("expDate").value.toString()
                    val balance = dataSnapshot.child("balance").value.toString().toDouble()
                    val name = dataSnapshot.child("name").value.toString()

//                    println("ckeck:${teacherName}")

                    val pay = Payment(cartNo,name,expDate,cvv,balance)
                    callback(paycheck)
                } else {
                    callback(null) // Teacher not found
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                callback(null)
            }
        })
    }

    //gettting single userdata passing the userID
    fun getSingleUserData(id:String, callback: (Double?) -> Unit){
        var ref = databaseReference.child("User").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val paycheck = dataSnapshot.getValue(User::class.java)
                    callback(paycheck!!.balance)
                } else {
                    callback(null) // Teacher not found
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during the data retrieval
                callback(null)
            }
        })
    }

//    fun checkPayment(payment: Payment, userID:String, callback: (Boolean) -> Unit){
//
//        this.getSinglePaymentData(payment.cardNo!!) {pay ->
//            if(pay!= null){
//                println("data:${pay}")
//                if(pay.expDate == payment.expDate && pay.cvv == payment.cvv){
//
//                    //checking the balance is enough or not
//                    if(pay.balance!! >= payment.balance!!){
//                        println("balance is enough")
//                        this.updatePaymentBalance(payment.balance!!, pay)
//                        this.updateWalletBalance(userID, payment.balance!!)
//                        callback(true)
//                    }
//
//                }
//            }
//        }
//        return callback(false)
//
//    }

    //validate the cart details and update the payment balance and user wallet balance
fun checkPayment(payment: Payment, userID:String, context:Context){

    this.getSinglePaymentData(payment.cardNo!!) {pay ->
        if(pay!= null){
            println("data:${pay} adn ${userID}")
            if(pay.expDate == payment.expDate && pay.cvv == payment.cvv){

                //checking the balance is enough or not
                if(pay.balance!! >= payment.balance!!){
                    this.updatePaymentBalance(payment.balance!!, pay)
                    this.updateWalletBalance(userID, payment.balance!!){
                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }


                }else{
                    Toast.makeText(context, "Balance is insufficient", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "cart invalid", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(context, "cart invalid", Toast.LENGTH_SHORT).show()
        }
    }
}
}