package me.vladsmirnov.cocktailapp.data.network.abstracts

import me.vladsmirnov.cocktailapp.data.models.NetworkResult
import me.vladsmirnov.cocktailapp.data.network.exceptions.NoConnectionException
import me.vladsmirnov.cocktailapp.data.network.exceptions.NotFoundException
import me.vladsmirnov.cocktailapp.data.network.exceptions.UnknownException
import retrofit2.HttpException
import retrofit2.Response
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class AbstractNetworkService {

    protected suspend fun <T : Any> createCall(call: suspend () -> Response<T>): NetworkResult<T> {

        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            t.printStackTrace()
            return NetworkResult.Error(mapToNetworkError(t))
        }

        if (response.isSuccessful) {
            if (response.body() != null) {
                return NetworkResult.Success(response.body()!!)
            }
        } else {
            val errorBody = response.errorBody()
            return if (errorBody != null) {
                NetworkResult.Error(mapApiException(response.code()))
            } else NetworkResult.Error(mapApiException(0))
        }
        return NetworkResult.Error(HttpException(response))
    }

    private fun mapApiException(code: Int): Exception {
        return when (code) {
            HttpURLConnection.HTTP_NOT_FOUND -> NotFoundException()
            else -> UnknownException()
        }
    }

    private fun mapToNetworkError(t: Throwable): Exception {
        return when (t) {
            is SocketTimeoutException -> SocketTimeoutException("Connection Timed Out")
            is UnknownHostException -> NoConnectionException()
            else -> UnknownException()

        }
    }
}