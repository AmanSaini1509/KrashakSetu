package com.example.krashaksetu.components

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

object PreferencesKeys {
    val LANGUAGE = stringPreferencesKey("language")
}

suspend fun saveLanguage(context: Context, languageCode: String) {
    context.dataStore.edit { preferences ->
        preferences[PreferencesKeys.LANGUAGE] = languageCode
    }

}

fun getSavedLanguage(context: Context): kotlinx.coroutines.flow.Flow<String> {
    return context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LANGUAGE] ?: "en"
    }

}