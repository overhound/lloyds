package com.ot.playground.techtest.model

import com.google.gson.JsonSyntaxException
import org.json.JSONObject
import retrofit2.Response

suspend fun <T : Any> apiCall(
    call: suspend () -> Response<T>,
    errorMessage: String = "Some errors occurred, Please try again later"
): Result<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
                android.util.Log.e("response", response.body().toString())
            response.body()?.let {
                return Result.Success(it)
            }
        }
        response.errorBody()?.let {
            return try {
                android.util.Log.e("faaf", it.toString())
                val errorString = it.toString()
                val errorObject = JSONObject(errorString)
                Result.Error(
                    RuntimeException(if (errorObject.has("message")) errorObject.getString("message") else "Error occurred, Try again Later")
                )
            } catch (ignored: JsonSyntaxException) {
                Result.Error(RuntimeException(errorMessage))
            }
        }
        return Result.Error(RuntimeException(errorMessage))
    } catch (e: Exception) {
        return Result.Error(RuntimeException(errorMessage))
    }
}

