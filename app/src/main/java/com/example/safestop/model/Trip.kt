package com.example.safestop.model

import android.os.Parcel
import android.os.Parcelable

//setting trip model as the data class
data class Trip(
    var tripId: String? =  null,
    var selectedTime: String? = null,
    var numPassengers: Int? = null,
    var price: Double? = null,
    var cost: Double? = null,
    var selectedDate: String? = null,
    var seatNumbers: String? = null
)