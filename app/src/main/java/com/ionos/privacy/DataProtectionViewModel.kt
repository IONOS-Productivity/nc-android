package com.ionos.privacy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ionos.analycis.AnalyticsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Provider

class DataProtectionViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager,
    private val privacyPreferences: PrivacyPreferences,
) : ViewModel() {

    private var _stateFlow = MutableStateFlow(State())
    val stateFlow = _stateFlow.asStateFlow()

    fun onAgreeButtonClick() {
        save(isAnalyticsEnabled = true)
    }

    fun onRejectLinkClick() {
        save(isAnalyticsEnabled = false)
    }

    fun onSettingsButtonClick() {
        _stateFlow.update { it.copy(page = Page.DETAIL) }
    }

    fun onDetailPageBackButtonClick() {
        _stateFlow.update { it.copy(page = Page.OVERVIEW) }
    }

    fun onAnalyticsCheckedChange(isChecked: Boolean) {
        _stateFlow.update { it.copy(isAnalyticsEnabled = isChecked) }
    }

    fun onSaveButtonClick() {
        save(stateFlow.value.isAnalyticsEnabled)
    }

    private fun save(isAnalyticsEnabled: Boolean) {
        analyticsManager.setEnabled(isAnalyticsEnabled)
        privacyPreferences.setAnalyticsEnabled(isAnalyticsEnabled)
        privacyPreferences.setDataProtectionProcessed(true)
        _stateFlow.update { it.copy(isProcessed = true) }
    }

    enum class Page(val index: Int) {
        OVERVIEW(0),
        DETAIL(1),
    }

    data class State(
        val page: Page = Page.OVERVIEW,
        val isAnalyticsEnabled: Boolean = false,
        val isProcessed: Boolean = false,
    )

    class Factory @Inject constructor(
        private val viewModelProvider: Provider<DataProtectionViewModel>,
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return viewModelProvider.get() as T
        }
    }
}
