package com.example.safestop.TestCase

object TotCostTest {

    private val timeToPriceMap = mapOf(
        "9:00 AM" to 600.0,
        "11:00 AM" to 560.0,
        "2:00 PM" to 450.0,
        "4:00 PM" to 375.0,
        "6:00 PM" to 700.0
    )
    fun calculateCost(selectedTime: String, numPassengers: Int): Double {
        val price = timeToPriceMap[selectedTime] ?: 0.0
        return price * numPassengers
    }

}