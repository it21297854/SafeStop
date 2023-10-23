package com.example.safestop.TestCase

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test

@RunWith(JUnit4::class)
class TotCostTestTest{
    @Test
    fun testCalculateCost9AM() {
        // Set the selected time and number of passengers
        val selectedTime = "9:00 AM"
        val numPassengers = 4

        // Act: Calculate the cost using the activity's function
        val cost = TotCostTest.calculateCost(selectedTime, numPassengers)

        // Assert that the calculated cost matches the expected value
        assertThat(cost).isEqualTo(2400.0) // Use the expected value here
    }

    @Test
    fun testCalculateCost6PM() {
        // Set the selected time and number of passengers
        val selectedTime = "6:00 PM"
        val numPassengers = 2

        // Act: Calculate the cost using the activity's function
        val cost = TotCostTest.calculateCost(selectedTime, numPassengers)

        // Assert that the calculated cost matches the expected value
        assertThat(cost).isEqualTo(1400.0) // Use the expected value here
    }
}