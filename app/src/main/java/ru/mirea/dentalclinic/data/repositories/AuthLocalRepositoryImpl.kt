package ru.mirea.dentalclinic.data.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import ru.mirea.dentalclinic.data.utils.AuthScheme
import ru.mirea.dentalclinic.domain.repositories.AuthLocalRepository
import javax.inject.Inject

class AuthLocalRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : AuthLocalRepository {
    private val repositoryScope = CoroutineScope(SupervisorJob()+Dispatchers.Main)

    override val isAuthorized: StateFlow<Boolean> = dataStore.data.map { prefs ->
        prefs[AuthScheme.AUTH_TOKEN] != null
    }.stateIn(repositoryScope, SharingStarted.Lazily, false)

    override suspend fun getAuthToken(): String? {
        return dataStore.data.map { preferences ->
            preferences[AuthScheme.AUTH_TOKEN]
        }.first()
    }

    override suspend fun logout() {
        dataStore.edit { prefs ->
            prefs.remove(AuthScheme.AUTH_TOKEN)
        }
    }

    override suspend fun login(token: String) {
        dataStore.edit { prefs ->
            prefs[AuthScheme.AUTH_TOKEN] = token
        }
    }
}