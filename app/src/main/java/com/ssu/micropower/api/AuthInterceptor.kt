package com.ssu.micropower.api

import com.ssu.micropower.app.SessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionManager: SessionManager
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = sessionManager.getAccessToken()
        if (token != null) {
            val headers = chain.request().headers.newBuilder()
                .add("Authorization", token)
                .build()

            val request = chain.request().newBuilder().headers(headers).build()
            return chain.proceed(request)
        }

        return chain.proceed(chain.request())
    }
}