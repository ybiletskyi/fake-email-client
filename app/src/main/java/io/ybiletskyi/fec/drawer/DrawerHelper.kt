package io.ybiletskyi.fec.drawer

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.viewmodels.FiltersViewModel
import io.ybiletskyi.fec.R

class DrawerHelper(
    private val ownerActivity: AppCompatActivity,
    private val viewModel: FiltersViewModel
) {

    var enableDrawer: Boolean
        get() = drawerToggle.isDrawerIndicatorEnabled
        set(value) {
            drawerToggle.isDrawerIndicatorEnabled = value

            if (value) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }

    private val drawerToggle: ActionBarDrawerToggle
    private val drawerLayout: DrawerLayout
    private val recyclerView: RecyclerView
    private val adapter: DrawerAdapter

    private val drawerOpened: Boolean
        get() = drawerLayout.isDrawerVisible(GravityCompat.START)

    init {
        recyclerView = ownerActivity.findViewById<RecyclerView>(R.id.drawer_recycler_view).also {
            it.layoutManager = LinearLayoutManager(ownerActivity)
            it.itemAnimator = null
        }

        drawerLayout = ownerActivity.findViewById(R.id.drawer_layout)
        drawerToggle = ActionBarDrawerToggle(ownerActivity, drawerLayout, ownerActivity.findViewById(R.id.main_nav_toolbar), R.string.drawer_open, R.string.drawer_close)
        drawerToggle.setToolbarNavigationClickListener { ownerActivity.onSupportNavigateUp() }

        adapter = setupAdapter()
    }

    fun syncState() {
        drawerToggle.syncState()
    }

    fun handleOnBackPressed(): Boolean {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
            return true
        }

        return false
    }

    fun onRestoreInstanceState() {
        if (drawerOpened) {
            updateDataSet()
        }
    }

    private fun setupAdapter(): DrawerAdapter {
        val adapter = DrawerAdapter()
        adapter.itemClickListener = object : ItemClickListener {
            override fun onItemClick(item: DrawerItem) {
                handleMenuItemClick(item)
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
            }
        }

        var oldState = DrawerLayout.STATE_IDLE
        drawerLayout.addDrawerListener(object : DrawerLayout.SimpleDrawerListener() {
            override fun onDrawerStateChanged(newState: Int) {
                if (!drawerOpened && oldState == DrawerLayout.STATE_IDLE && newState != oldState) {
                    updateDataSet()
                }
                oldState = newState
            }
        })

        recyclerView.adapter = adapter
        return adapter
    }

    private fun handleMenuItemClick(item: DrawerItem) {
        viewModel.applyFilter(item)
    }

    private fun updateDataSet() {
        val dataSet = mutableListOf<DrawerData>()
        dataSet.add(DrawerData.Header)

        val currentItem = viewModel.emailsFilter.value ?: DrawerItem.values().first()
        for (item in DrawerItem.values()) {
            dataSet.add(DrawerData.Folder(item, item == currentItem))
        }

        adapter.setData(dataSet)
    }

    sealed class DrawerData {
        object Header: DrawerData()
        data class Folder(val item: DrawerItem, val isActivated: Boolean): DrawerData()
    }
}