package com.ionos.privacy

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ionos.utils.context.isDarkMode
import com.nextcloud.utils.extensions.getParcelableArgument
import com.owncloud.android.databinding.ActivityDataProtectionBinding
import com.owncloud.android.ui.activity.BaseActivity
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class DataProtectionActivity : BaseActivity() {

    @Inject
    lateinit var viewModelFactory: DataProtectionViewModel.Factory

    private val viewModel by viewModels<DataProtectionViewModel> { viewModelFactory }

    private val binding by lazy { ActivityDataProtectionBinding.inflate(layoutInflater) }

    private val detailPageOnBackPressedCallback by lazy {
        onBackPressedDispatcher.addCallback(this) { viewModel.onDetailPageBackButtonClick() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(SystemBarStyle.dark(Color.TRANSPARENT), SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.overviewPage.agreeButton.setOnClickListener { viewModel.onAgreeButtonClick() }
        binding.overviewPage.settingsButton.setOnClickListener { viewModel.onSettingsButtonClick() }
        binding.detailPage.backButton.setOnClickListener { viewModel.onDetailPageBackButtonClick() }
        binding.detailPage.saveButton.setOnClickListener { viewModel.onSaveButtonClick() }
        binding.detailPage.analyticsSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onAnalyticsCheckedChange(isChecked)
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { rootView, windowInsets ->
            val insetsType = WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            val insets = windowInsets.getInsets(insetsType)
            binding.overviewPage.root.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            binding.detailPage.root.setPadding(insets.left, insets.top, insets.right, insets.bottom)
            ViewCompat.onApplyWindowInsets(rootView, windowInsets)
        }

        viewModel.stateFlow
            .flowWithLifecycle(lifecycle)
            .onEach(::updateState)
            .launchIn(lifecycleScope)
    }

    private fun updateState(state: DataProtectionViewModel.State) {
        if (binding.viewSwitcher.displayedChild != state.page.index) {
            binding.viewSwitcher.displayedChild = state.page.index
        }
        if (state.page == DataProtectionViewModel.Page.OVERVIEW) {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_YES
            setSystemBarsAppearance(false)
            detailPageOnBackPressedCallback.isEnabled = false
        } else {
            delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
            setSystemBarsAppearance(!isDarkMode())
            detailPageOnBackPressedCallback.isEnabled = true
        }
        if (binding.detailPage.analyticsSwitch.isChecked != state.isAnalyticsEnabled) {
            binding.detailPage.analyticsSwitch.isChecked = state.isAnalyticsEnabled
        }
        if (state.isProcessed) {
            intent.getParcelableArgument(TARGET_SCREEN_INTENT_KEY, Intent::class.java)?.let(::startActivity)
            finish()
        }
    }

    private fun setSystemBarsAppearance(isLight: Boolean) {
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = isLight
        controller.isAppearanceLightNavigationBars = isLight
    }

    companion object {
        private const val TARGET_SCREEN_INTENT_KEY = "target_screen_intent"

        @JvmStatic
        fun createIntent(context: Context): Intent {
            return Intent(context, DataProtectionActivity::class.java)
        }

        @JvmStatic
        fun createIntent(context: Context, targetScreenIntent: Intent): Intent {
            return Intent(context, DataProtectionActivity::class.java)
                .putExtra(TARGET_SCREEN_INTENT_KEY, targetScreenIntent)
        }
    }
}
