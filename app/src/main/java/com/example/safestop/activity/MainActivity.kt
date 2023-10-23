package com.example.safestop.activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.safestop.R
import com.example.safestop.database.FirebaseHelper
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private var firebaase = FirebaseHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        // register all the Buttons with their appropriate IDs
        val todoB: Button = findViewById(R.id.todoB)
        val logoutButton: Button = findViewById(R.id.logoutButton)

        // register all the card views with their appropriate IDs
        val contributeCard: CardView = findViewById(R.id.contributeCard)
        val analyticsCard: CardView = findViewById(R.id.analyticsCard)
        val searchCard: CardView = findViewById(R.id.search_card)
        val bookTripCard: CardView = findViewById(R.id.bookTripCard)
        val helpCard: CardView = findViewById(R.id.helpCard)
        val settingsCard: CardView = findViewById(R.id.settingsCard)

        //setting balance to the wallet
        var walletBalance:TextView = findViewById(R.id.txt_Wallet)
        var userID = intent.getStringExtra("userID")
        firebaase.getSingleUserData(userID!!) {user ->
            walletBalance.text = user!!.balance.toString()
        }


        // handle top up button
        todoB.setOnClickListener {
            //redirect to the topup page and passing the userID data
            val intent = Intent(this, TopUp::class.java)
            intent.putExtra("userID", userID)
            startActivity(intent)
            finish()
        }

        //handle the logout button
        logoutButton.setOnClickListener {
            mAuth.signOut()
            Toast.makeText(this, "Session Logging out", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }


        // handle each of the cards with the OnClickListener
        contributeCard.setOnClickListener {
        }
        analyticsCard.setOnClickListener {
            val i = Intent(this, AnalyticsActivity::class.java)
            startActivity(i)
        }
        searchCard.setOnClickListener {
            Toast.makeText(this, "Navigating to the Available Busses", Toast.LENGTH_SHORT).show()
            val i = Intent(this, SearchBusesActivity::class.java)
            startActivity(i)
        }
        bookTripCard.setOnClickListener {
            Toast.makeText(this, "Navigating to the Book a Trip page", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, BookTripCardActivity::class.java)
            startActivity(intent)
        }
        helpCard.setOnClickListener {
        }
        settingsCard.setOnClickListener {

        }
    }
}
