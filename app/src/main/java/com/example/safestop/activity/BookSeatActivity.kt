package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.safestop.R

class BookSeatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_seat)

        val back: Button = findViewById(R.id.back)

        back.setOnClickListener{
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}