package com.example.baseapp.data.remote

import com.example.baseapp.utils.LogUtils
import retrofit2.Response

const val TAG = "API Request"


data class SportMonksResponse<T>(
    val data: T,
    val pagination: PaginationDto? = null,
    val rate_limit: RateLimitDto? = null,
    val timezone: String? = null
)

data class RateLimitDto(
    val resets_in_seconds: Int? = null,
    val remaining: Int? = null,
    val requested_entity: String? = null
)

data class PaginationDto(
    val count: Int? = null,
    val per_page: Int? = null,
    val has_more: Boolean? = null,
    val current_page: Int? = null,
    val next_page: String? = null
)

inline fun <T : Any> apiCall(request: () -> Response<T>): Resource<T> {
    return try {
        val result = request.invoke()

        if (result.isSuccessful) {
            val resultBody = result.body()
            LogUtils.d(TAG, "apiCall successful =${resultBody}")
            return Resource.Success(resultBody!!)
        } else {
            LogUtils.e(TAG, "apiCall result.code() =${result.code()}")
            Resource.Error(
                message = result.errorBody()?.string()
                    ?: "Unknown error",
                code = result.code()
            )
        }

    } catch (e: Exception) {
        Resource.Error(
            message = e.message ?: "Network error",
            code = null
        )
    }
}
