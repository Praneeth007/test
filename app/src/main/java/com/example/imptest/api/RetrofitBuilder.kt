package com.example.myapplication.api

import android.util.Log
import com.example.imptest.BuildConfig
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


object RetrofitBuilder {
    private const val TIMEOUT: Long = 5000

    private fun getRetrofit(
        session: Boolean = false,
        accept: String = "application/json"
    ): Retrofit {
        val b = OkHttpClient.Builder()
        b.connectTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        b.readTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
        b.writeTimeout(TIMEOUT, TimeUnit.MILLISECONDS)
//        b.sslSocketFactory(SSLManager.getSslSocketFactory(), SSLManager.getTrustManager())
        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

                if (BuildConfig.DEBUG) {
                    Log.i("OKHTTP:", message)
                }
            }
        })
//        logging.level = HttpLoggingInterceptor.Level.HEADERS
        logging.level = HttpLoggingInterceptor.Level.BODY
        b.interceptors().add(object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val request: Request = chain.request()

                // try the request
                var response: Response = chain.proceed(request)
                var tryCount = 0
                while (isResponseFailed(response) && tryCount < 2) {
//                    println("Response: ${response.code} & ${response.isSuccessful}")
                    Log.d("intercept", "Request is not successful - $tryCount")
                    tryCount++
                    // retry the request
                    response = chain.proceed(request)
                }
                // otherwise just pass the original response on
                return response
            }
        })
        val okHttpClient: OkHttpClient.Builder =
            b.addInterceptor(logging).addInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val original = chain.request()
                    val builder = original.newBuilder()
                        .header("Accept", accept)

                    builder.method(original.method, original.body)
                    return chain.proceed(builder.build())
                }
            })
        okHttpClient.retryOnConnectionFailure(true)
        return Retrofit.Builder()
            .baseUrl("https://data.covid19india.org/")
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(okHttpClient.build())
            .build()
    }

    private fun isResponseFailed(response: Response): Boolean {
        if (!response.isSuccessful) {
            if (response.code == 404) {
                return false
            }
            return true
        }
        return false
    }

    val noSessionService: ApiService = getRetrofit(session = false).create(ApiService::class.java)
//    val sessionService: ApiService = getRetrofit(session = true).create(ApiService::class.java)
    val retrofit = getRetrofit(session = true)
}