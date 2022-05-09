package com.example.myapplication.api

import okhttp3.Headers

class Resource<T> private constructor(
    val status: Status,
    val headers: Headers?,
    val data: T?,
    val error: ResourceError?
) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?, headers: Headers? = null): Resource<T> {
            return Resource(Status.SUCCESS, headers, data, null)
        }

        fun <T> success404(resourceError: ResourceError?, headers: Headers? = null): Resource<T> {
            return Resource(Status.SUCCESS, headers, null, resourceError)
        }

        fun <T> error(resourceError: ResourceError?): Resource<T> {
            return Resource(Status.ERROR, null, null, resourceError)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, null, data, null)
        }
    }
}