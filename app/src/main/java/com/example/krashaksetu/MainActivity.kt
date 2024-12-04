package com.example.krashaksetu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.krashaksetu.components.KrashakSetuApp
import com.example.krashaksetu.components.setLocale
import com.example.krashaksetu.ui.theme.KrashakSetuTheme
import com.example.krashaksetu.viewModels.LanguageViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KrashakSetuTheme {
                // Access the LanguageViewModel
                val viewModel: LanguageViewModel = viewModel()
                val selectedLanguage = viewModel.selectedLanguage.collectAsState(initial = "en").value

                // Update the locale based on selected languagee
                val updatedContext = applicationContext.setLocale(selectedLanguage)

                // Provide updated context for localization
                CompositionLocalProvider(LocalContext provides this) {
                    KrashakSetuApp(viewModel, updatedContext)
                }
            }
        }
    }
}
