package com.example.eko8757.footballclubfinal.ui.myactivity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.ui.myfragment.favoritematch.FragmentFavorite
import com.example.eko8757.footballclubfinal.ui.myfragment.schedulematch.FragmentSchedule
import com.example.eko8757.footballclubfinal.ui.myfragment.team.FragmentTeam
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var fragment: Fragment
    private lateinit var fragmentManager: FragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottom_navigation.inflateMenu(R.menu.bottom_navigation_menu)
        fragmentManager = supportFragmentManager

        fragmentManager.beginTransaction().replace(R.id.mainContainer, FragmentSchedule()).commit()

        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            val id = item.getItemId()
            when (id) {
                R.id.schedule_menu -> fragment = FragmentSchedule()
                R.id.team_menu -> fragment = FragmentTeam()
                R.id.favorite_menu -> fragment = FragmentFavorite()
            }
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.mainContainer, fragment)
            transaction.commit()
            true
        }
    }
}
