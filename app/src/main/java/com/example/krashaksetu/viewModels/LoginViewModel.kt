package com.example.krashaksetu.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var aadharNumber = mutableStateOf("")
    var password = mutableStateOf("")

    var isLoginSuccessful = mutableStateOf(false)
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf("")

    // Dummy credentials for testing
    private val dummyAadharNumber = "123456789012"
    private val dummyPassword = "123@4"

    // Callback for login result
    var onLoginSuccess: (() -> Unit)? = null
    var onLoginFailure: (() -> Unit)? = null

    // Function to authenticate the user
    fun authenticateUser() {
        // Reset error message and show loading state
        errorMessage.value = ""
        isLoading.value = true

        // Simulate a delay for authentication (can be replaced with actual API call)
        val isValidAadhar = aadharNumber.value.length == 12
        val isValidPassword = password.value.isNotBlank()

        if (!isValidAadhar || !isValidPassword) {
            isLoading.value = false
            errorMessage.value = "Please enter valid Aadhaar number and password"
            onLoginFailure?.invoke()
            return
        }

        // Verify credentials
        if (aadharNumber.value == dummyAadharNumber && password.value == dummyPassword) {
            isLoading.value = false
            isLoginSuccessful.value = true
            onLoginSuccess?.invoke()
        } else {
            isLoading.value = false
            errorMessage.value = "Invalid Aadhaar number or password"
            onLoginFailure?.invoke()
        }
    }
}
