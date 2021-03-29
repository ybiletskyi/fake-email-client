package io.ybiletskyi.fec.drawer

import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.ybiletskyi.fec.R

class DrawerHelper(ownerActivity: AppCompatActivity, toolbar: Toolbar) {

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
        drawerToggle = ActionBarDrawerToggle(ownerActivity, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close)
        drawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu)

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
        when (item) {
            DrawerItem.Inbox -> {

            }
            DrawerItem.Trash -> {

            }
        }
    }

    private fun updateDataSet() {
        val dataSet = mutableListOf(
            DrawerData.Header,
            DrawerData.Folder(DrawerItem.Inbox, true),
            DrawerData.Folder(DrawerItem.Trash, false)
        )

        adapter.setData(dataSet)
    }

    sealed class DrawerData {
        object Header: DrawerData()
        data class Folder(val item: DrawerItem, val isActivated: Boolean): DrawerData()
    }
}