package com.sample.myapplication.data.network

import com.sample.myapplication.model.Result
import com.sample.myapplication.model.network.CategoriesResponse
import com.sample.myapplication.model.network.RecepiesResponse
import com.sample.myapplication.network.RestInterface
import com.sample.myapplication.util.ErrorUtils
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CategoriesRemoteDataSource @Inject constructor(private val retrofit: Retrofit, private val apiService: RestInterface) {

    suspend fun fetchCategories(): Result<CategoriesResponse> {

        return getResponse(
            request = { apiService.getCategories() },
            defaultErrorMessage = "Error fetching Movie list"
        )
    }

    private suspend fun <T> getResponse(request: suspend () -> Response<T>, defaultErrorMessage: String): Result<T> {
        return try {
            println("I'm working in thread ${Thread.currentThread().name}")
            val result = request.invoke()
            if (result.isSuccessful) {
                return Result.success(result.body())
            } else {
                val errorResponse = ErrorUtils.parseError(result, retrofit)
                Result.error(errorResponse?.status_message ?: defaultErrorMessage, errorResponse)
            }
        } catch (e: Throwable) {
            Result.error("Unknown Error", null)
        }
    }
}