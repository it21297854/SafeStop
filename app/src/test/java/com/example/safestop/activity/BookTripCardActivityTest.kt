package com.example.safestop.TestCase

import org.junit.runners.JUnit4
import com.example.safestop.activity.BookTripCardActivity
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(JUnit4::class)
class BookTripCardActivityTest {

    @Test
    fun testCalculateCost() {
        // Set the selected time and number of passengers
        val selectedTime = "9:00 AM"
        val numPassengers = 4

        // Act: Calculate the cost using the activity's function
        val cost = BookTripCardActivity().calculateCost(selectedTime, numPassengers)

        // Assert that the calculated cost matches the expected value
        assertThat(cost).isEqualTo(2400.0) // Use the expected value here
    }

    @Test
    fun testCalculateCost2() {
        // Set the selected time and number of passengers
        val selectedTime = "9:00 AM"
        val numPassengers = 4

        // Act: Calculate the cost using the activity's function
        val cost = BookTripCardActivity().calculateCost(selectedTime, numPassengers)

        // Assert that the calculated cost matches the expected value
        assertThat(cost).isEqualTo(2000.0) // Use the expected value here
    }
}

