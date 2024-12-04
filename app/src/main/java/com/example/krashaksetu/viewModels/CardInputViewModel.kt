package com.example.krashaksetu.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CardInputViewModel : ViewModel() {
    var cardNumber = mutableStateOf("")

    private val dummyCardNumber = "123456789012"

    var isCardValid = mutableStateOf(false)
    var invalidCard = mutableStateOf("")

    var onSubmitClick: (() -> Unit)? = null

    fun verifyCard(inputCardNumber: String): Boolean {
        return if (inputCardNumber == dummyCardNumber) {
            isCardValid.value = true
            invalidCard.value = ""
            onSubmitClick?.invoke()
            true // Card is valid
        } else {
            isCardValid.value = false
            invalidCard.value = "Invalid Card Number. Try again"
            false // Card is invalid
        }
    }
}
