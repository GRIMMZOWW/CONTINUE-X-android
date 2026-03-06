package com.bhaumik.continuex.data.repository

import android.util.Log
import com.google.gson.Gson
import com.bhaumik.continuex.data.api.ApiService
import com.bhaumik.continuex.data.api.GenerateRequest
import com.bhaumik.continuex.data.api.RetrofitClient

class CapsuleRepository(
    private val apiService: ApiService = RetrofitClient.apiService
) {
    suspend fun generateCapsule(chatText: String, style: String): Result<String> {
        return try {
            val safeStyle = when (style.lowercase().trim()) {
                "brief" -> "brief"
                "detailed" -> "detailed"
                "code", "code-focused", "code focused" -> "code"
                else -> "brief"
            }

            val request = GenerateRequest(
                chatText = chatText,
                style = safeStyle
            )
            
            Log.d("CONTINUEX", "Sending request - style: ${request.style}")
            Log.d("CONTINUEX", "Chat text length: ${request.chatText.length}")
            Log.d("CONTINUEX", "Chat text preview: ${request.chatText.take(100)}")
            Log.d("CONTINUEX", "Full Request JSON: ${Gson().toJson(request)}")
            
            val response = apiService.generateCapsule(request)
            Log.d("CONTINUEX", "Response code: ${response.code()}")
            
            if (response.isSuccessful) {
                val body = response.body()
                Log.d("CONTINUEX", "Response body: $body")
                if (body?.capsule != null) {
                    Result.success(body.capsule)
                } else {
                    val errMsg = body?.error ?: "Capsule is null"
                    Log.e("CONTINUEX", "Error from body: $errMsg")
                    Result.failure(Exception(errMsg))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Log.e("CONTINUEX", "API Error Body: $errorBody")
                Result.failure(Exception("API Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("CONTINUEX", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }
}
