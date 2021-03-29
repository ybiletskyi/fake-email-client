package io.ybiletskyi.fec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ybiletskyi.fec.drawer.DrawerHelper

class MainActivity : AppCompatActivity() {

    private val toolbarHelper by lazy {
        ToolbarHelper(this)
    }

    private val drawerHelper by lazy {
        DrawerHelper(this, toolbarHelper.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        drawerHelper.syncState()
    }

    override fun onBackPressed() {
        if (!drawerHelper.handleOnBackPressed())
            super.onBackPressed()
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        drawerHelper.onRestoreInstanceState()
    }
}