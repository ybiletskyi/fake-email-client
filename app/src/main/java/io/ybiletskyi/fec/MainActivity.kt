package io.ybiletskyi.fec

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.drawer.DrawerHelper
import io.ybiletskyi.fec.utils.EmailCreator
import io.ybiletskyi.fec.utils.ToolbarHelper
import io.ybiletskyi.fec.viewmodels.FiltersViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FiltersViewModel
    private lateinit var newEmailButton: FloatingActionButton
    val appRouter = AppRouter(this)

    private val toolbarHelper by lazy {
        ToolbarHelper(this)
    }

    private val drawerHelper by lazy {
        DrawerHelper(this, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // perform initialization of toolbar
        toolbarHelper
        // init view model
        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
        // open main fragment, if activity is not recreated
        if (savedInstanceState == null) {
            appRouter.openMainScreen()
        }
        // emulate new email receiving
        newEmailButton = findViewById(R.id.emulate_email)
        newEmailButton.setOnClickListener {
            viewModel.emulateNewEmail()
        }
        // handle intent
        onNewIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        val extras = intent?.extras
        if (extras != null && extras.containsKey(EmailCreator.EMAIL_ID)) {
            appRouter.openDetails(extras.getInt(EmailCreator.EMAIL_ID))
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // sync drawer state
        drawerHelper.syncState()
    }

    override fun onBackPressed() {
        if (!drawerHelper.handleOnBackPressed() && !appRouter.onBackPressed())
            super.onBackPressed()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        drawerHelper.onRestoreInstanceState()
    }

    fun applyScreenSettings(screenSettings: ScreenSettings) {
        when (screenSettings) {
            is ScreenSettings.Default -> {
                drawerHelper.enableDrawer = true
                toolbarHelper.setUpHomeButton(ToolbarHelper.ButtonState.BURGER)
                toolbarHelper.setUpTitle(screenSettings.title)
            }
            is ScreenSettings.Details -> {
                drawerHelper.enableDrawer = false
                toolbarHelper.setUpHomeButton(ToolbarHelper.ButtonState.BACK_BUTTON)
                toolbarHelper.setUpTitle(null) // hide title
            }
        }
    }
}