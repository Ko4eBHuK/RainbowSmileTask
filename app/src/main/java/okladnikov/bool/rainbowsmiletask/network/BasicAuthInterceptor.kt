package okladnikov.bool.rainbowsmiletask.network

import android.util.Log
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(
    user: String,
    password: String
) : Interceptor {

    private val credentials: String = Credentials.basic(user, password)

    override fun intercept(chain: Interceptor.Chain): Response {
        Log.e(this.javaClass.simpleName, credentials)

        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Authorization", credentials)
                .build()
        )
    }
}