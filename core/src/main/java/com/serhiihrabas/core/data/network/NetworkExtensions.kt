package com.serhiihrabas.core.data.network

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.TimeoutCancellationException
import java.io.IOException
import java.net.UnknownHostException

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T): NetworkResult<T>()
    data class Error(val message: String, val code: Int? = null): NetworkResult<Nothing>()
}

suspend inline fun <reified T> safeCall(
    crossinline call: suspend () -> HttpResponse
): NetworkResult<T> {
    return try {
        val response = call()
        if (response.status.value in 200..299) {
            val body = response.body<T>()
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(
                message = response.status.description,
                code = response.status.value
            )
        }
    } catch (e: TimeoutCancellationException) {
        NetworkResult.Error("Request timed out")
    } catch (e: UnknownHostException) {
        NetworkResult.Error("No internet connection")
    } catch (e: IOException) {
        NetworkResult.Error("Network error")
    } catch (e: Exception) {
        NetworkResult.Error("Unexpected error: ${e.localizedMessage}")
    }
}