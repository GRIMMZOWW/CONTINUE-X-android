package com.bhaumik.continuex.data.repository

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
            
            
            val response = apiService.generateCapsule("api/generate", null, request)
            
            if (response.isSuccessful) {
                val bodyText = response.body()
                if (bodyText?.capsule != null) {
                    Result.success(bodyText.capsule)
                } else {
                    Result.failure(Exception("Failed to generate capsule. Please try again."))
                }
            } else {
                Result.failure(Exception("Service is currently unavailable. Please verify your connection or API key."))
            }
        } catch (e: Exception) {
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
                    Result.failure(Exception("Failed to generate resume prompt."))
                }
            } else {
                Result.failure(Exception("Failed to connect to the server."))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
