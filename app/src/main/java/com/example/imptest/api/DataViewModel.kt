package com.example.myapplication.api

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class DataViewModel : ViewModel() {
    private val dataAPiCall = NetworkCall<TotalResponse>()

    fun fetchData(): LiveData<Resource<TotalResponse>> {
        return dataAPiCall.makeCall(
                RetrofitBuilder.noSessionService.getData()
            )

    }

     val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

//    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
//    fun destroy() {
//        loginCall.cancel()
//    }
}