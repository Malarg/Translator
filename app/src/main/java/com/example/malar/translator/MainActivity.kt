package com.example.malar.translator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.malar.translator.adapters.MainViewPagerAdapter
import com.example.malar.translator.di.App
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.main_toolbar))
        viewPager.adapter = MainViewPagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
    }
}
