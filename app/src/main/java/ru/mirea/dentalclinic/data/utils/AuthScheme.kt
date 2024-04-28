package ru.mirea.dentalclinic.data.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object AuthScheme {
    val AUTH_TOKEN = stringPreferencesKey("auth_token")
}