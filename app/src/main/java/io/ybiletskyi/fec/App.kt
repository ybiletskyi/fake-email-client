package io.ybiletskyi.fec

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        val context: Context
            get() = app.applicationContext

        private lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}