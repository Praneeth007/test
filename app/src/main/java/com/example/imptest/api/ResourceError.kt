package com.example.myapplication.api



@androidx.annotation.Keep
data class ResourceError(
    val code: Int,
    var description: String,
    val recommendation: String?
) {
    fun asString(): String {
//        ApiErrors.getErrorMessage(code)?.let {
//            return it
//        }
        recommendation?.let {
            return "$description\n$recommendation"
        }
        return description
    }
}