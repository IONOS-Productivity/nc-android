package com.ionos.scanbot.screens.base

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.ionos.scanbot.R
import com.ionos.scanbot.controller.ScanbotController
import com.ionos.scanbot.di.inject
import com.ionos.scanbot.screens.base.BaseScreen.Event
import com.ionos.scanbot.screens.base.BaseScreen.State
import com.ionos.scanbot.screens.base.BaseScreen.ViewModel

internal abstract class BaseActivity<E : Event, S : State<E>, VM : ViewModel<E, S>> : AppCompatActivity() {
    protected abstract val viewModelFactory: ViewModelProvider.Factory
    protected abstract val viewBinding: ViewBinding

    protected val context: Context get() = this
    protected val viewModel: VM by viewModels { viewModelFactory }
    protected val scanbotController: ScanbotController by inject { scanbotController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = ContextCompat.getColor(context, R.color.system_bar_read_mode_color)
        setContentView(viewBinding.root)
        viewModel.state.observe(this) { it.renderInternal() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.onResume()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        scanbotController.restoreState(savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        scanbotController.saveState(outState)
        super.onSaveInstanceState(outState)
    }

    final override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    protected fun showMessage(@StringRes messageRes: Int) {
        val message = getString(messageRes)
        showMessage(message)
    }

    protected fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun S.renderInternal() {
        render()
        event?.handleInternal()
    }

    private fun E.handleInternal() {
        handle()
        viewModel.onEventHandled()
    }

    protected abstract fun S.render()

    protected abstract fun E.handle()
}
