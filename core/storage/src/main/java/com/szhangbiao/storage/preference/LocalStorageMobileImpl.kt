package com.szhangbiao.storage.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.VisibleForTesting
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class LocalStorageMobileImpl(context: Context) : ILocalStorage {

    companion object {
        private const val SHARED_PREF = "SHARED_PREF"
        private const val SHARED_PREF_ENCRYPTED = "SHARED_PREF_PROTECTED"

        private const val SERIAL_NUMBER = "SERIAL_NUMBER"
        private const val USER_ID = "USER_ID"
        private const val USER_NAME = "USER_NAME"
        private const val DARK_MODE = "DARK_MODE"
        private const val BUYBACK_EMAIL = "BUYBACK_EMAIL"
        private const val INITIAL_SETUP_TIME = "INITIAL_SETUP_TIME"
        private const val LAST_USER_SESSION = "LAST_USER_SESSION"
    }

    private val masterKeyAlias = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val sharedPreferences: SharedPreferences = EncryptedSharedPreferences.create(
        context,
        SHARED_PREF_ENCRYPTED,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val oldSharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREF,
        Context.MODE_PRIVATE
    )

    init {
        // check if we have old values stored
        val oldMap = oldSharedPreferences.all
        if (oldMap.isNotEmpty()) {
            // migrate old values
            sharedPreferences.edit {
                oldMap.forEach { (key, value) ->
                    val currentKey = key.substring(key.lastIndexOf(".") + 1)
                    when (value) {
                        is Int -> putInt(currentKey, value)
                        is Long -> putLong(currentKey, value)
                        is Boolean -> putBoolean(currentKey, value)
                        is String -> putString(currentKey, value)
                    }
                }
            }
            // Remove records form previous shared preferences
            oldSharedPreferences.edit {
                clear()
            }
        }
    }

    override var deviceSerialNumber: String
        get() = sharedPreferences.getString(SERIAL_NUMBER, "").orEmpty()
        set(value) = sharedPreferences.edit {
            putString(SERIAL_NUMBER, value)
        }

    override var userId: Int
        get() = sharedPreferences.getInt(USER_ID, 0)
        set(value) = sharedPreferences.edit {
            putInt(USER_ID, value)
        }

    override var lastBuyBackEmail: String
        get() = sharedPreferences.getString(BUYBACK_EMAIL, "").orEmpty()
        set(value) = sharedPreferences.edit {
            putString(BUYBACK_EMAIL, value)
        }

    override var userName: String
        get() = sharedPreferences.getString(USER_NAME, "").orEmpty()
        set(value) = sharedPreferences.edit {
            putString(USER_NAME, value)
        }

    override var initialSetupTime: Long
        get() = sharedPreferences.getLong(INITIAL_SETUP_TIME, 0)
        set(value) = sharedPreferences.edit {
            putLong(INITIAL_SETUP_TIME, value)
        }

    override var lastUserSession: Long
        get() = sharedPreferences.getLong(LAST_USER_SESSION, -1)
        set(value) =
            sharedPreferences.edit {
                putLong(LAST_USER_SESSION, value)
            }

    override fun getDarkModeEnabled(): Boolean = sharedPreferences.getBoolean(DARK_MODE, false)

    override fun toggleDarkMode() {
        val darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE, false)
        sharedPreferences.edit {
            putBoolean(DARK_MODE, !darkModeEnabled)
        }
    }

    override fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
