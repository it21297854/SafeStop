package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.safestop.R
import com.google.firebase.database.FirebaseDatabase
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BookTripCardActivity : AppCompatActivity() {
    private lateinit var viewSeats: Button
    private lateinit var personEditText: EditText
    private lateinit var timeSpinner: Spinner
    private lateinit var seatNumbersEditText: EditText
    private lateinit var totalCost: TextView
    private val timeToPriceMap = mapOf(
        "9:00 AM" to 600.0,
        "11:00 AM" to 560.0,
        "2:00 PM" to 450.0,
        "4:00 PM" to 375.0,
        "6:00 PM" to 700.0
    )
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.getReference("BookTrip")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_trip_card)

        val dateEditText = findViewById<EditText>(R.id.date_edit_text)
        // Calculate the date for the next day
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, 1)
        val nextDay = calendar.time
        seatNumbersEditText = findViewById(R.id.SeatNumbers)

        // Format the date as a string in the desired format (e.g., "MM/dd/yyyy")
        val dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
        val nextDayString = dateFormat.format(nextDay)

        // Set the formatted date as the initial text for the EditText
        dateEditText.setText(nextDayString)
        timeSpinner = findViewById(R.id.time_spinner)
        val timeOptions = arrayOf("9:00 AM", "11:00 AM", "2:00 PM", "4:00 PM", "6:00 PM")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, timeOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        timeSpinner.adapter = adapter

        personEditText = findViewById(R.id.person)
        totalCost = findViewById(R.id.totalCost)

        timeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                updateTotalCost()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Handle nothing selected if needed
            }
        }

        personEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Not needed for this example
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                updateTotalCost()
            }

            override fun afterTextChanged(s: Editable?) {
                // Not needed for this example
            }
        })

        viewSeats = findViewById(R.id.bookTripButton)
        viewSeats.setOnClickListener {

            val selectedTime = timeSpinner.selectedItem.toString()
            val numPassengers = personEditText.text.toString().toIntOrNull() ?: 0
            val price = timeToPriceMap[selectedTime] ?: 0.0
            val cost = price * numPassengers
            totalCost.text = "Total Cost: $cost LKR"

            // Save the trip details to Firebase
            val selectedDate = dateEditText.text.toString()
            val seatNumbers = seatNumbersEditText.text.toString()
            val tripDetails = mutableMapOf(

                "selectedTime" to selectedTime,
                "numPassengers" to numPassengers,
                "price" to price,
                "cost" to cost,
                "selectedDate" to selectedDate,
                "seatNumbers" to seatNumbers
            )

            val tripRef = databaseReference.child("trips").push()
            val tripId = tripRef.key
            tripDetails["tripId"] = tripId.toString()
            tripRef.setValue(tripDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this,"Use Analyze page for complete the payment", Toast.LENGTH_SHORT).show()
                        val i = Intent(this, MainActivity::class.java)
                        startActivity(i)
                    } else {
                        Toast.makeText(this,"Error Occurred when saving", Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }

    private fun updateTotalCost() {
        val selectedTime = timeSpinner.selectedItem.toString()
        val numPassengers = personEditText.text.toString().toIntOrNull() ?: 0
        val price = timeToPriceMap[selectedTime] ?: 0.0
        val cost = price * numPassengers
        totalCost.text = "Total Cost: $cost LKR"
    }
}
