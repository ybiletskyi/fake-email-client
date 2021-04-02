package io.ybiletskyi.fec

import io.ybiletskyi.fec.common.fragment.BaseFragment
import io.ybiletskyi.fec.details.DetailsFragment
import io.ybiletskyi.fec.main.MainFragment

class AppRouter(private val activity: MainActivity) {

    fun openMainScreen() {
        val mainFragment = MainFragment()
        navigateTo(mainFragment)
    }

    fun openDetails(emailId: Int) {
        val detailsFragment = DetailsFragment.newInstance(emailId)
        navigateTo(detailsFragment)
    }

    fun onBackPressed(): Boolean {
        val manager = activity.supportFragmentManager
        return when (manager.backStackEntryCount) {
            // first item is root, so app will close in this case
            1 -> {
                activity.finish()
                true
            }
            // in other cases activity will handle the fragments back stack
            else -> false
        }
    }

    // TODO: need to add handling of fragment duplication in the backs tack
    private fun navigateTo(fragment: BaseFragment) {
        activity.supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, fragment)
            .addToBackStack(fragment::class.java.simpleName)
            .commit()
    }
}