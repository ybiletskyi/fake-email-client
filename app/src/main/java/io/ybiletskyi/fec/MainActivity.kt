package io.ybiletskyi.fec

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import io.ybiletskyi.fec.drawer.DrawerHelper

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: FiltersViewModel

    private val toolbarHelper by lazy {
        ToolbarHelper(this)
    }

    private val drawerHelper by lazy {
        DrawerHelper(this, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(FiltersViewModel::class.java)
        toolbarHelper
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