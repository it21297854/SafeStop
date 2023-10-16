package com.example.safestop.database

import com.example.safestop.model.Payment
import com.example.safestop.model.User
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

//    fun updateTask(task: mainModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
//        databaseReference.child(task.id!!).setValue(task)
//            .addOnSuccessListener {
//                onSuccess()
//            }
//            .addOnFailureListener { exception ->
//                onFailure(exception)
//            }
//    }
//
//
//    fun deleteTask(id: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
//        databaseReference.child(id).removeValue()
//            .addOnSuccessListener {
//                onSuccess()
//            }
//            .addOnFailureListener { exception ->
//                onFailure(exception)
//            }
//    }
//
//    fun getTasks(listener: ValueEventListener) {
//        databaseReference.addValueEventListener(listener)
//    }


    fun createPayment(payment: Payment, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {



        val taskRef = databaseReference.child("payment").child(payment.payID!!)
//        task.id = taskRef.key // Assign the generated key as the task ID
        taskRef.setValue(payment)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }
}