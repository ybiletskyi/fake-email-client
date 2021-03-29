package io.ybiletskyi.fec

import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ToolbarHelper(
    private val ownerActivity: AppCompatActivity
) {

    val toolbar: Toolbar
    val actionbar: ActionBar

    init {
        toolbar = ownerActivity.findViewById(R.id.main_nav_toolbar)
        ownerActivity.setSupportActionBar(toolbar)
        actionbar = ownerActivity.supportActionBar!!
    }
}