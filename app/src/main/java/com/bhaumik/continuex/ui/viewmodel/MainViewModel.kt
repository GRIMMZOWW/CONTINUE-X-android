package com.bhaumik.continuex.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bhaumik.continuex.data.repository.CapsuleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val capsule: String) : UiState()
    data class Error(val message: String) : UiState()
}

class MainViewModel(
    private val repository: CapsuleRepository = CapsuleRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedStyle = MutableStateFlow("brief")
    val selectedStyle: StateFlow<String> = _selectedStyle.asStateFlow()

    private val _chatText = MutableStateFlow("")
    val chatText: StateFlow<String> = _chatText.asStateFlow()

    private val _resumePrompt = MutableStateFlow("")
    val resumePrompt: StateFlow<String> = _resumePrompt.asStateFlow()

    private val _isLoadingPrompt = MutableStateFlow(false)
    val isLoadingPrompt: StateFlow<Boolean> = _isLoadingPrompt.asStateFlow()

    fun updateChatText(text: String) {
        _chatText.value = text
    }

    fun updateStyle(style: String) {
        _selectedStyle.value = style
    }

    fun generateCapsule(
        customApiKey: String? = null,
        customProvider: String? = null,
        customModel: String? = null
    ) {
        if (_chatText.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _resumePrompt.value = ""
            val result = repository.generateCapsule(
                chatText = _chatText.value,
                style = _selectedStyle.value,
                customApiKey = customApiKey,
                customProvider = customProvider,
                customModel = customModel
            )
            result.onSuccess { capsule ->
                _uiState.value = UiState.Success(capsule)
                // Trigger the second API call to generate the resume prompt
                generateRemoteResumePrompt(capsule, customApiKey, customProvider, customModel)
            }
            result.onFailure { exception ->
                _uiState.value = UiState.Error(exception.message ?: "An unexpected error occurred")
            }
        }
    }

    private fun generateRemoteResumePrompt(
        capsuleText: String,
        customApiKey: String?,
        customProvider: String?,
        customModel: String?
    ) {
        viewModelScope.launch {
            _isLoadingPrompt.value = true
            val promptResult = repository.generateResumePrompt(capsuleText, customApiKey, customProvider, customModel)
            promptResult.onSuccess { prompt ->
                _resumePrompt.value = prompt
            }
            promptResult.onFailure {
                // Fallback prompt if the network call fails
                _resumePrompt.value = "Here is my context capsule from my last AI session. \nPlease read it and continue from where we left off:\n\n$capsuleText"
            }
            _isLoadingPrompt.value = false
        }
    }
    
    fun resetState() {
        _chatText.value = ""
        _selectedStyle.value = "brief"
        _uiState.value = UiState.Idle
        _resumePrompt.value = ""
    }
}
