package com.example.safestop.database

import com.example.safestop.model.Payment
import com.example.safestop.model.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FirebaseHelper {
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

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

    fun createPayment(payment: Payment, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {



        val taskRef = databaseReference.child("payment").child(payment.cardNo!!)
//        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(payment)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    fun updateWalletBalance(userID:String, balance: Double) {

        this.getSingleUserData(userID){user ->

            if(user != null){
                var newBalance = User(user.userID,balance + user.balance!!)
                databaseReference.child("User").child(user.userID!!).setValue(newBalance)
            }

        }
    }

    fun updatePaymentBalance(balance:Double, payment: Payment) {
        var newBalance = Payment(payment.cardNo,payment.name,payment.expDate,payment.cvv,payment.balance!! - balance)
        databaseReference.child("payment").child(payment.cardNo!!).setValue(newBalance)

    }

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

    fun getSingleUserData(id:String, callback: (User?) -> Unit){
        var ref = databaseReference.child("User").child(id)
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val paycheck = dataSnapshot.getValue(User::class.java)
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

    fun checkPayment(payment: Payment, userID:String){

        this.getSinglePaymentData(payment.cardNo!!) {pay ->
            if(pay!= null){
                println("data:${pay}")
                if(pay.expDate == payment.expDate && pay.cvv == payment.cvv){

                    //checking the balance is enough or not
                    if(pay.balance!! >= payment.balance!!){
                        println("balance is enough")
                        this.updatePaymentBalance(payment.balance!!, pay)
                        this.updateWalletBalance(userID, payment.balance!!)
                    }

                }
            }
        }

    }
}