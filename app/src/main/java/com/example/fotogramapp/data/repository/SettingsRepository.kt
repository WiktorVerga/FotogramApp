package com.example.fotogramapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.first

class SettingsRepository(private val dataStore: DataStore<Preferences>) {
    //Insieme di chiavi utilizzabili
    private companion object {
        val LOGGED_USER_ID = intPreferencesKey("logged_user")
        val SESSION_ID = stringPreferencesKey("session_id")
    }

    //Getters & Setters
    suspend fun isFirstAccess(): Boolean {
        val prefs = dataStore.data.first()
        return prefs[LOGGED_USER_ID] == null
    }

    suspend fun getLoggedUserId(): Int? {
        val prefs = dataStore.data.first()
        return prefs[LOGGED_USER_ID]
    }

    suspend fun setLoggedUser(userId: Int, sessonId: String) {
        dataStore.edit { preferences ->
            preferences[LOGGED_USER_ID] = userId
            preferences[SESSION_ID] = sessonId
        }
    }

    suspend fun getSessionId(): String? {
        val prefs = dataStore.data.first()
        return prefs[SESSION_ID]
    }
}