package com.example.safestop.activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.safestop.R


class SearchBusesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_buses)

        val seatArrangement: Button = findViewById(R.id.seatArrangement)

        seatArrangement.setOnClickListener{
            Toast.makeText(this, "Navigating to the Seats Arrangements", Toast.LENGTH_SHORT).show()
            val i = Intent(this, BookSeatActivity::class.java)
            startActivity(i)
        }
    }
}