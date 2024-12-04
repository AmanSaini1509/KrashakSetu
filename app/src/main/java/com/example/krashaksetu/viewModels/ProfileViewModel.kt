package com.example.krashaksetu.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// ViewModel to manage profile data and state
class ProfileViewModel : ViewModel() {
    var isEditing by mutableStateOf(false)
        private set

    // Dummy data for profile
    var mobileNumber by mutableStateOf("+918005673279")
    var email by mutableStateOf("user@example.com")
    var profileImageUrl by mutableStateOf("https://t4.ftcdn.net/jpg/06/30/06/81/360_F_630068155_RnZI6mC91wz7gUYFVmhzwpl4O6x00Cbh.jpg") // Placeholder image URL

    val state = "Rajasthan"
    val district = "Jaipur"
    val tehsil = "Sanganer"
    val village = "Badanpura"
    val pinCode = "303905"
    val maritalStatus = "-"
    val annualIncome = "-"
    val highestEducation = "-"
    val age = "0"

    fun toggleEditing() {
        isEditing = !isEditing
    }

    fun updateMobileNumber(newNumber: String) {
        mobileNumber = newNumber
    }

    fun updateEmail(newEmail: String) {
        email = newEmail
    }

    fun updateProfileImageUrl(newUrl: String) {
        profileImageUrl = newUrl
    }
}