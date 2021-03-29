package io.ybiletskyi.fec.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.ybiletskyi.fec.AppRouter
import io.ybiletskyi.fec.MainActivity
import io.ybiletskyi.fec.common.ScreenSettings

abstract class BaseFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(getLayoutId(), container, false)
    }

    override fun onResume() {
        super.onResume()
        // apply fragment screen settings
        getMainActivity().applyScreenSettings(getScreenSettings())
    }

    abstract fun getLayoutId(): Int

    abstract fun getScreenSettings(): ScreenSettings

    fun getMainActivity(): MainActivity {
        return activity as MainActivity
    }

    fun getAppRouter(): AppRouter {
        return getMainActivity().appRouter
    }
}