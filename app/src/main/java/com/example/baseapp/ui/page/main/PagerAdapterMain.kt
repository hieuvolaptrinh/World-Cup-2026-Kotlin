package com.example.baseapp.ui.page.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseapp.ui.page.main.home.FragmentHome

class PagerAdapterMain(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_HOME -> FragmentHome()
            else -> Fragment()
        }
    }

    companion object {
        const val POSITION_HOME = 0
        const val POSITION_LIB = 1
        const val POSITION_SETTINGS = 2
    }
}