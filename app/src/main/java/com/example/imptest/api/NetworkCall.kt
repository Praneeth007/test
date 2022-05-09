package com.example.myapplication.api

import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.RetrofitBuilder.retrofit
import com.google.gson.reflect.TypeToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class NetworkCall<T> {
    lateinit var call: Call<T>

    fun makeCall(call: Call<T>): MutableLiveData<Resource<T>> {
        this.call = call
        val callBackKt = CallBackKt<T>()
        callBackKt.result.value = Resource.loading(null)
        this.call.clone().enqueue(callBackKt)
        return callBackKt.result
    }

    class CallBackKt<T> : Callback<T> {
        var result: MutableLiveData<Resource<T>> = MutableLiveData()

        override fun onFailure(call: Call<T>, t: Throwable) {
            result.value = Resource.error(
                ResourceError(
                    code = 0,
//                    description = t.localizedMessage ?: "",
                    description = "" ,
                    recommendation = "Something went wrong. Try again."
                )
            )
            t.printStackTrace()
        }

        override fun onResponse(call: Call<T>, response: Response<T>) {
            when {
                response.isSuccessful -> {

                    result.value = Resource.success(response.body(), response.headers())
                }
                response.code() == 404 -> {
                    result.value = Resource.success404(parseError(response.errorBody()))
                }
                else -> {
                    result.value = Resource.error(parseError(response.errorBody()))
                }
            }
        }
    }

    fun cancel() {
        if (::call.isInitialized) {
            call.cancel()
        }
    }
}

private fun parseError(response: ResponseBody?): ResourceError? {
    val type = object : TypeToken<List<ResourceError>>() {
    }.type
    val converter =
        retrofit.responseBodyConverter<List<ResourceError>>(type, arrayOfNulls<Annotation>(0))
    try {
        response?.let { rb ->
            converter.convert(rb)?.let { errorList ->
                return if (errorList.isEmpty()) null else errorList[0]
            }
        }
    } catch (e: Exception) {
    }
    return null
}