package com.bhaumik.continuex.data.api

import com.google.gson.annotations.SerializedName
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

data class GenerateRequest(
    @SerializedName("chatText") val chatText: String,
    @SerializedName("style") val style: String
)

data class GenerateResponse(
    @SerializedName("capsule") val capsule: String? = null,
    @SerializedName("error") val error: String? = null
)

interface ApiService {
    @POST("api/generate")
    suspend fun generateCapsule(
        @Body request: GenerateRequest
    ): Response<GenerateResponse>
}
