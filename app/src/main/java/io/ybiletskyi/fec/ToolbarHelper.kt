package io.ybiletskyi.fec

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class ToolbarHelper(
    ownerActivity: AppCompatActivity
) {

    init {
        val toolbar = ownerActivity.findViewById<Toolbar>(R.id.main_nav_toolbar)
        ownerActivity.setSupportActionBar(toolbar)
    }
}