package com.bhaumik.continuex.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class GenerateRequest(
    @SerializedName("chatText") val chatText: String,
    @SerializedName("style") val style: String,
    @SerializedName("customApiKey") val customApiKey: String? = null,
    @SerializedName("customProvider") val customProvider: String? = null,
    @SerializedName("customModel") val customModel: String? = null,
    @SerializedName("isResumePrompt") val isResumePrompt: Boolean? = null
)

data class GenerateResponse(
    @SerializedName("capsule") val capsule: String? = null,
    @SerializedName("resumePrompt") val resumePrompt: String? = null,
    @SerializedName("error") val error: String? = null
)

interface ApiService {
    @POST
    suspend fun generateCapsule(
        @retrofit2.http.Url url: String,
        @retrofit2.http.Header("Authorization") authHeader: String?,
        @Body request: GenerateRequest
    ): Response<GenerateResponse>
}
