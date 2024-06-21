package com.mostafadevo.marvelous.data.remote

import com.mostafadevo.marvelous.BuildConfig
import com.mostafadevo.marvelous.Utils.toMd5
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private val publicApiKey = BuildConfig.PUBLIC_API_KEY
    private val privateApiKey = BuildConfig.PRIVATE_API_KEY

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val ts = System.currentTimeMillis().toString()
        val hash = "$ts$privateApiKey$publicApiKey".toMd5()
        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("apikey", publicApiKey)
            .addQueryParameter("ts", ts)
            .addQueryParameter("hash", hash)
            .build()

        val request = originalRequest.newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}