package com.worldcup.app.data.remote

sealed class Resource<T> {
    class Success<T>(val data: T) : Resource<T>()
    class Error<T>(
        val message: String,
        val code: Int? = null
    ) : Resource<T>()
    class Loading<T> : Resource<T>()
}