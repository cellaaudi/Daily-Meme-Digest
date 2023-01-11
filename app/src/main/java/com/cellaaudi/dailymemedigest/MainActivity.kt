package com.cellaaudi.dailymemedigest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*

class MainActivity : AppCompatActivity() {
    companion object {
        var USER_ID = ""
    }

    val fragments: ArrayList<Fragment> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        var sharedFile = "com.cellaaudi.dailymemedigest"
        var shared: SharedPreferences = getSharedPreferences(sharedFile, Context.MODE_PRIVATE)
        var user_id = shared.getInt("USER_ID", 0)

        // Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Daily Meme Digest"

        // Hamburger Menu
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        var drawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name)
        drawerToggle.isDrawerIndicatorEnabled = true
        drawerToggle.syncState()

        fragments.add(HomeFragment())
        fragments.add(MineFragment())
        fragments.add(LeaderboardFragment())
        fragments.add(SettingsFragment())

        navView.setNavigationItemSelectedListener {
            viewPager.currentItem = when(it.itemId) {
                R.id.itemHome -> 0
                R.id.itemMine -> 1
                R.id.itemLeaderboard -> 2
                R.id.itemSettings -> 3

                else -> 0
            }

            true
        }

        viewPager.adapter = MyAdapter(this, fragments)

        viewPager.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                navView.setCheckedItem(bottomNav.menu.getItem(position).itemId)
                bottomNav.selectedItemId = bottomNav.menu.getItem(position).itemId
            }
        })

        bottomNav.setOnItemSelectedListener {
            viewPager.currentItem = when(it.itemId) {
                R.id.itemHome -> 0
                R.id.itemMine -> 1
                R.id.itemLeaderboard -> 2
                R.id.itemSettings -> 3

                else -> 0
            }

            true
        }
    }
}