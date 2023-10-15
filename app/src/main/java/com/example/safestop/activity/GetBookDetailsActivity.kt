package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.safestop.R
import com.example.safestop.model.Trip
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class GetBookDetailsActivity : AppCompatActivity() {
    private lateinit var editTextSelectedTime: EditText
    private lateinit var editTextNumPassengers: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var editTextCost: EditText
    private lateinit var editTextSeatNumbers: EditText
    private lateinit var updateButton: Button
    private lateinit var tripId: String

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("BookTrip")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_get_book_details)

        editTextSelectedTime = findViewById(R.id.editTextSelectedTime)
        editTextNumPassengers = findViewById(R.id.editTextNumPassengers)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextCost = findViewById(R.id.editTextCost)
        editTextSeatNumbers = findViewById(R.id.editTextSeatNumbers)
        updateButton = findViewById(R.id.button_Update)

        // Retrieve individual values from the intent
        val selectedTime = intent.getStringExtra("selectedTime")
        val numPassengers = intent.getIntExtra("numPassengers", 0)
        val price = intent.getDoubleExtra("price", 0.0)
        val cost = intent.getDoubleExtra("cost", 0.0)
        val seatNumbers = intent.getStringExtra("seatNumbers")

        // Set the retrieved values in the EditText fields
        editTextSelectedTime.setText(selectedTime)
        editTextNumPassengers.setText(numPassengers.toString())
        editTextPrice.setText(price.toString())
        editTextCost.setText(cost.toString())
        editTextSeatNumbers.setText(seatNumbers)

        tripId = intent.getStringExtra("tripId") ?: ""

        updateButton.setOnClickListener {
            val editedSelectedTime = editTextSelectedTime.text.toString()
            val editedNumPassengers = editTextNumPassengers.text.toString().toIntOrNull() ?: 0
            val editedPrice = editTextPrice.text.toString().toDoubleOrNull() ?: 0.0
            val editedCost = editTextCost.text.toString().toDoubleOrNull() ?: 0.0
            val editedSeatNumbers = editTextSeatNumbers.text.toString()

            val isSeatAvailable = isSeatAvailableInDatabase(editedSeatNumbers)

            if(isSeatAvailable){
                updateTripDetails(tripId, editedSelectedTime, editedNumPassengers, editedPrice, editedCost,editedSeatNumbers)
                val intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
            }
            else{
                val suggestedSeatNumber = suggestAvailableSeat()
                Toast.makeText(this, "Seat is already taken. Suggested available seat: $suggestedSeatNumber", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateTripDetails(
        tripId: String,
        selectedTime: String,
        numPassengers: Int,
        price: Double,
        cost: Double,
        seatNumbers: String
    ) {
        val tripRef = databaseReference.child("trips").child(tripId)
        val updatedTrip = Trip(
            tripId = tripId,
            selectedTime = selectedTime,
            numPassengers = numPassengers,
            price = price,
            cost = cost,
            seatNumbers = seatNumbers
        )

        tripRef.setValue(updatedTrip)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Trip details updated", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Error occurred when updating", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun isSeatAvailableInDatabase(seatNumbers: String): Boolean {
        // You should implement this method to check if the seat number is available in your database
        // Query your database to check if the seatNumbers exist

        // Assuming you have a list of reserved seat numbers in your database
        val reservedSeats = listOf("3", "7", "12", "15", "20") // Replace with your database data

        // Check if the entered seat numbers exist in the list of reserved seats
        return !reservedSeats.contains(seatNumbers)
    }

    private fun suggestAvailableSeat(): String {
        // You should implement this method to suggest an available seat number
        // Query your database to find the first available seat number

        // Assuming you have a list of reserved seat numbers in your database
        val reservedSeats = listOf("3", "7", "12", "15", "20") // Replace with your database data

        // Suggest the first available seat number between 1 and 45
        for (i in 1..45) {
            val seatNumber = i.toString()
            if (!reservedSeats.contains(seatNumber)) {
                return seatNumber
            }
        }

        // If all seats are taken, return a message or handle it according to your requirements
        return "All seats are taken"
    }

}
