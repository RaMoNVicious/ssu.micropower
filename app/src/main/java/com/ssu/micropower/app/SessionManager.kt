package com.ssu.micropower.app

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.ssu.micropower.models.domain.UserSession
import com.ssu.micropower.models.domain.UserSessionRefresh
import org.koin.core.component.KoinComponent
import java.text.SimpleDateFormat
import java.util.Date

class SessionManager(context: Context) : KoinComponent {
    private val masterKeyAlias = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        APP_PREFERENCES,
        masterKeyAlias,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun sessionSave(session: UserSession) {
        sharedPreferences.edit()
            .putInt(SESSION_ID, session.sessionId)
            .putString(ACCESS_TOKEN, session.accessToken)
            .putString(ACCESS_TOKEN_EXP, session.accessTokenExpiry)
            .putString(REFRESH_TOKEN, session.refreshToken)
            .putString(REFRESH_TOKEN_EXP, session.refreshTokenExpiry)
            .apply()
    }

    fun sessionUpdate(session: UserSessionRefresh) {
        sharedPreferences.edit()
            .putString(ACCESS_TOKEN, session.accessToken)
            //.putString(ACCESS_TOKEN_EXP, session.accessTokenExpiry) // format mismatch
            .putString(REFRESH_TOKEN, session.refreshToken)
            //.putString(REFRESH_TOKEN_EXP, session.refreshTokenExpiry) // format mismatch
            .apply()
    }

    fun getAccessToken(): String? {
        return getToken(ACCESS_TOKEN, ACCESS_TOKEN_EXP)
    }

    fun getRefreshToken(): String? {
        return getToken(REFRESH_TOKEN, REFRESH_TOKEN_EXP)
    }

    private fun getToken(tokenName: String, tokenExp: String): String? {
        val token = sharedPreferences.getString(tokenName, null) ?: return null

        return sharedPreferences
            .getString(tokenExp, null)
            ?.let {
                SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(it)
            }
            ?.takeIf { it.after(Date()) }
            ?.let {
                token
            }
    }

    fun getSession(): Int {
        return sharedPreferences.getInt(SESSION_ID, -1)
    }


    fun clearSession() {
        sharedPreferences.edit()
            .remove(SESSION_ID)
            .remove(ACCESS_TOKEN)
            .remove(ACCESS_TOKEN_EXP)
            .remove(REFRESH_TOKEN)
            .remove(REFRESH_TOKEN_EXP)
            .apply()
    }

    companion object {
        private const val APP_PREFERENCES = "app_preferences"
        private const val SESSION_ID = "session_id"
        private const val ACCESS_TOKEN = "access_token"
        private const val ACCESS_TOKEN_EXP = "access_token_exp"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val REFRESH_TOKEN_EXP = "refresh_token_exp"
    }
}