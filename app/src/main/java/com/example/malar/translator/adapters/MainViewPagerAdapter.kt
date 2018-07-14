package com.example.malar.translator.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.malar.translator.fragments.FavouritesFragment
import com.example.malar.translator.fragments.TranslatorFragment

class MainViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getCount(): Int = 2

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> TranslatorFragment.newInstance()
            1 -> FavouritesFragment.newInstance()
            else -> TranslatorFragment.newInstance()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position) {
            0 -> "translate"
            1 -> "favourite"
            else -> "nothing"
        }
    }
}