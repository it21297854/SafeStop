package com.example.safestop.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.safestop.R
import com.example.safestop.adapter.TripAdapter
import com.example.safestop.model.Trip
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AnalyticsActivity : AppCompatActivity() {
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference: DatabaseReference = database.getReference("BookTrip") // Replace with your path

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analytics)

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

//        val toolbar = findViewById<Toolbar>(R.id.toolbarBookedTrips)
//        setSupportActionBar(toolbar)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val adapter = TripAdapter()
        recyclerView.adapter = adapter

        val query = databaseReference.child("trips")
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val tripsList: MutableList<Trip> = mutableListOf()

                for (tripSnapshot in dataSnapshot.children) {
                    val trip = tripSnapshot.getValue(Trip::class.java)
                    trip?.let { tripsList.add(it) }
                }

                adapter.submitList(tripsList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors that occur during data retrieval
            }
        })

        // Implement item click listeners for editing and deleting items
        adapter.setOnItemClickListener(object : TripAdapter.OnItemClickListener {
            override fun onEditClick(position: Int, trip: Trip) {
                // Handle edit button click
                // You can start a new activity to edit the selected trip
                val intent = Intent(this@AnalyticsActivity, GetBookDetailsActivity::class.java)
                intent.putExtra("selectedTime", trip.selectedTime)
                intent.putExtra("numPassengers", trip.numPassengers)
                intent.putExtra("price", trip.price)
                intent.putExtra("seatNumbers", trip.seatNumbers)
                intent.putExtra("cost", trip.cost)
                intent.putExtra("tripId", trip.tripId)
                startActivity(intent)
            }

            override fun onDeleteClick(position: Int, trip: Trip) {
                // Handle delete button click
                val tripId = trip.tripId

                // Get a reference to the specific trip to be deleted in the Firebase database
                val tripRef = databaseReference.child("trips").child(tripId.toString())

                // Remove the trip data from the database
                tripRef.removeValue()
            }
        })
    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                finish() // Close the current activity and navigate back
//                true
//            }
//
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
}
