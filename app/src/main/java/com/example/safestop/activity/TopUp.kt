package com.example.safestop.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.safestop.R
import com.example.safestop.database.FirebaseHelper
import com.example.safestop.model.Payment

class TopUp : AppCompatActivity() {
    val firebaseHelper = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top_up)

        var txt_amount:EditText = findViewById(R.id.txt_amount)
        var txt_name:EditText = findViewById(R.id.txt_name)
        var txt_cartNo:EditText = findViewById(R.id.txt_cartNo)
        var txt_exp:EditText = findViewById(R.id.txt_exp)
        var txt_cvv:EditText = findViewById(R.id.txt_cvv)
        var btn_pay:Button = findViewById(R.id.pay)

        btn_pay.setOnClickListener {
            var newPayment = Payment(
                "first",
                cardNo = txt_cartNo.text.toString(),
                name = txt_name.text.toString(),
                expDate = txt_exp.text.toString(),
                cvv = txt_cvv.text.toString().toInt(),
                balance = txt_amount.text.toString().toDouble()
            )
            Log.e("1234", "${newPayment}")

            firebaseHelper.createPayment(newPayment, {
                // Task creation was successful
                Toast.makeText(this, "Task created successfully", Toast.LENGTH_SHORT).show()

                // Optionally, clear the input fields
                txt_amount.text.clear()
                txt_cartNo.text.clear()
                txt_name.text.clear()
                txt_cvv.text.clear()
                txt_exp.text.clear()
            }, { exception ->
                // Task creation failed, handle the error
                Toast.makeText(this, "Task creation failed: ${exception.message}", Toast.LENGTH_SHORT).show()
            })
        }
    }
}