package io.ybiletskyi.fec.utils

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import io.ybiletskyi.fec.R

class ToolbarHelper(ownerActivity: AppCompatActivity) {
    private val actionBar: ActionBar

    init {
        val toolbar = ownerActivity.findViewById<Toolbar>(R.id.main_nav_toolbar)
        ownerActivity.setSupportActionBar(toolbar)
        actionBar = ownerActivity.supportActionBar!!
    }

    fun setUpHomeButton(state: ButtonState) {
        when (state) {
            ButtonState.BURGER -> {
                actionBar.setDisplayShowHomeEnabled(true)
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_menu)
            }
            ButtonState.BACK_BUTTON -> {
                actionBar.setDisplayShowHomeEnabled(true)
                actionBar.setDisplayHomeAsUpEnabled(true)
                actionBar.setHomeAsUpIndicator(R.drawable.ic_back_arrow)
            }
        }
    }

    fun setUpTitle(title: String?) {
        when {
            title != null -> {
                actionBar.setDisplayShowTitleEnabled(true)
                actionBar.title = title
            }
            else -> {
                actionBar.setDisplayShowTitleEnabled(false)
            }
        }
    }

    enum class ButtonState {
        BURGER,
        BACK_BUTTON
    }
}