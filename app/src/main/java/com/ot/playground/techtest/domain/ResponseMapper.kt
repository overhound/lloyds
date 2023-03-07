package com.ot.playground.techtest.model

import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import retrofit2.Response


suspend fun <T : Any> apiCall(
    call: suspend () -> Response<T>,
    errorMessage: String = "Some errors occurred, Please try again later"
): Result<T> {
    try {
        val response = call()
        if (response.isSuccessful) {
            response.body()?.let {
                return Result.Success(it)
            }
        }
        response.errorBody()?.let {
            val errorString = it.string()
            if (errorString.isNotBlank()) {
                return try {
                    val gsonBuilder = GsonBuilder().create()
                    val jsonElement = gsonBuilder.fromJson(errorString, JsonElement::class.java)
                    val errorObject = jsonElement.asJsonObject
                    Result.Error(
                        RuntimeException(if (errorObject.has("message")) errorObject.get("message").asString else "Error occurred, Try again Later")
                    )
                } catch (e: JSONException) {
                    Result.Error(RuntimeException(errorMessage))
                } catch (ignored: JsonSyntaxException) {
                    Result.Error(RuntimeException(errorMessage))
                }
            }
        }
        return Result.Error(RuntimeException(errorMessage))
    } catch (e: Exception) {
        return Result.Error(RuntimeException(e.localizedMessage))
    }
}

