package com.example.baseapp.ui.page.worldcup

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.baseapp.ui.page.worldcup.fixtures.FragmentScoresFixtures
import com.example.baseapp.ui.page.worldcup.home.FragmentWorldCupHome
import com.example.baseapp.ui.page.worldcup.standings.FragmentStandings

class PagerAdapterWorldCup(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            POSITION_HOME -> FragmentWorldCupHome()
            POSITION_FIXTURES -> FragmentScoresFixtures()
            POSITION_STANDINGS -> FragmentStandings()
            else -> Fragment()
        }
    }

    companion object {
        const val POSITION_HOME = 0
        const val POSITION_FIXTURES = 1
        const val POSITION_STANDINGS = 2
    }
}
