package io.ybiletskyi.fec.details

import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment

class DetailsFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_details

    override fun getScreenSettings(): ScreenSettings = ScreenSettings.Details
}