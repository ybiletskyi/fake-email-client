package io.ybiletskyi.fec.main

import android.os.Bundle
import android.view.View
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment

class MainFragment : BaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<View>(R.id.text).setOnClickListener {
            getAppRouter().openDetails()
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_main

    override fun getScreenSettings(): ScreenSettings = ScreenSettings.Default
}