package com.example.krashaksetu.components

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

fun Context.setLocale(languageCode: String): Context {
    val locale = Locale(languageCode) // Using java.util.Locale
    Locale.setDefault(locale) // Correct setDefault usage
    val config = resources.configuration
    config.setLocale(locale) // Set the new locale
    // Set the layout direction based on locale
    return createConfigurationContext(config)
}
