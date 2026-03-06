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

    fun updateChatText(text: String) {
        _chatText.value = text
    }

    fun updateStyle(style: String) {
        _selectedStyle.value = style
    }

    fun generateCapsule() {
        if (_chatText.value.isBlank()) return

        viewModelScope.launch {
            _uiState.value = UiState.Loading
            val result = repository.generateCapsule(_chatText.value, _selectedStyle.value)
            result.onSuccess { capsule ->
                _uiState.value = UiState.Success(capsule)
            }
            result.onFailure { exception ->
                _uiState.value = UiState.Error(exception.message ?: "An unexpected error occurred")
            }
        }
    }
    
    fun resetState() {
        _uiState.value = UiState.Idle
    }
}
