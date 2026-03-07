package com.bhaumik.continuex.data.repository

import android.util.Log
import com.google.gson.Gson
import com.bhaumik.continuex.data.api.ApiService
import com.bhaumik.continuex.data.api.GenerateRequest
import com.bhaumik.continuex.data.api.RetrofitClient

class CapsuleRepository(
    private val apiService: ApiService = RetrofitClient.apiService
) {
    suspend fun generateCapsule(
        chatText: String, 
        style: String,
        customApiKey: String? = null,
        customProvider: String? = null,
        customModel: String? = null
    ): Result<String> {
        return try {
            val safeStyle = when (style.lowercase().trim()) {
                "brief" -> "brief"
                "detailed" -> "detailed"
                "code", "code-focused", "code focused" -> "code"
                else -> "brief"
            }

            val request = GenerateRequest(
                chatText = chatText,
                style = safeStyle,
                customApiKey = if (!customApiKey.isNullOrEmpty()) customApiKey else null,
                customProvider = if (!customApiKey.isNullOrEmpty()) customProvider else null,
                customModel = if (!customApiKey.isNullOrEmpty()) customModel else null
            )
            
            Log.d("CONTINUEX", "Sending request with style: ${request.style}")
            Log.d("CONTINUEX", "Using custom key: ${!customApiKey.isNullOrEmpty()}")
            
            val response = apiService.generateCapsule("api/generate", null, request)
            Log.d("CONTINUEX", "Response code: ${response.code()}")
            
            if (response.isSuccessful) {
                val bodyText = response.body()
                if (bodyText?.capsule != null) {
                    Result.success(bodyText.capsule)
                } else {
                    val errMsg = bodyText?.error ?: "Capsule is null"
                    Result.failure(Exception(errMsg))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("API Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("CONTINUEX", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }

    suspend fun generateResumePrompt(
        capsuleText: String,
        customApiKey: String? = null,
        customProvider: String? = null,
        customModel: String? = null
    ): Result<String> {
        return try {
            val request = GenerateRequest(
                chatText = capsuleText,
                style = "brief",
                customApiKey = if (!customApiKey.isNullOrEmpty()) customApiKey else null,
                customProvider = if (!customApiKey.isNullOrEmpty()) customProvider else null,
                customModel = if (!customApiKey.isNullOrEmpty()) customModel else null,
                isResumePrompt = true
            )
            
            val response = apiService.generateCapsule("api/generate", null, request)
            
            if (response.isSuccessful) {
                val bodyText = response.body()
                if (bodyText?.resumePrompt != null) {
                    Result.success(bodyText.resumePrompt)
                } else {
                    val errMsg = bodyText?.error ?: "Resume prompt is null"
                    Result.failure(Exception(errMsg))
                }
            } else {
                val errorBody = response.errorBody()?.string() ?: "Unknown error"
                Result.failure(Exception("API Error ${response.code()}: $errorBody"))
            }
        } catch (e: Exception) {
            Log.e("CONTINUEX", "Exception: ${e.message}", e)
            Result.failure(e)
        }
    }
}
