package com.example.krashaksetu.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.krashaksetu.components.saveLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LanguageViewModel: ViewModel() {
    private val _selectedLanguage = MutableStateFlow("en") //Default to english
    val selectedLanguage: StateFlow<String> = _selectedLanguage

    fun updateLanguage(languageCode: String) {
        _selectedLanguage.value = languageCode

    }
}