package com.example.eko8757.footballclubfinal.main

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.*
import com.example.eko8757.footballclubfinal.ui.myactivity.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testRecyclerViewBehaviour() {
        Thread.sleep(5000)

        //Fragment Next
        //mengecek apakah RecyclerView Next telah muncul?
        Espresso.onView(ViewMatchers.withId(R.id.recycler_next)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //mengecek tab layout
        Espresso.onView(ViewMatchers.withId(R.id.tab_schedule)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //mengecek apakah spinner next telah muncul dan terdapat liga spanish?
        Espresso.onView(ViewMatchers.withId(R.id.spinner_next)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.spinner_next)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Spanish La Liga")).perform(ViewActions.click())
        Thread.sleep(3000)

        //setelah liga yang dipilih muncul maka swipeRefresh
        Espresso.onView(ViewMatchers.withId(R.id.swiperRefresh_next)).perform(ViewActions.swipeDown())
        Thread.sleep(3000)

        //scroll RecyclerView next ke posisi 5
        Espresso.onView(ViewMatchers.withId(R.id.recycler_next)).perform(RecyclerViewActions.scrollToPosition<AdapterEventList.EventViewHolder>(5))
        //setelah scroll maka lakukan click pada posisi yang telah ditentukan
        Espresso.onView(ViewMatchers.withId(R.id.recycler_next)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterEventList.EventViewHolder>(5, ViewActions.click()))
        Thread.sleep(3000)

        //setelah masuk kedalam detail recycler yg telah ditentukan, maka tambahkan detail tersebut kedalam favorite
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(3000)

        //lalu keluar dari detail tersebut
        Espresso.pressBack()
        Thread.sleep(3000)

        //swipe left untuk menuju fragment prev match
        Espresso.onView(ViewMatchers.withId(R.id.viewPager_schedule)).perform(ViewActions.swipeLeft())
        Thread.sleep(3000)

        //Fragment prev
        //apakah recyclerview prev telah muncul?
        Espresso.onView(ViewMatchers.withId(R.id.recycler_prev)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //cek tab layout
        Espresso.onView(ViewMatchers.withId(R.id.tab_schedule)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //cek spinner
        Espresso.onView(ViewMatchers.withId(R.id.spinner_prev)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)
        Espresso.onView(ViewMatchers.withId(R.id.spinner_prev)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Spanish La Liga")).perform(ViewActions.click())
        Thread.sleep(3000)

        //cek swiperefresh prev
        Espresso.onView(ViewMatchers.withId(R.id.swiperRefresh_prev)).perform(ViewActions.swipeDown())
        Thread.sleep(3000)

        //scroll to 5
        Espresso.onView(ViewMatchers.withId(R.id.recycler_prev)).perform(RecyclerViewActions.scrollToPosition<AdapterEventList.EventViewHolder>(7))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_prev)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterEventList.EventViewHolder>(7, ViewActions.click()))
        Thread.sleep(3000)

        //menambahkan ke favorite
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(3000)

        Espresso.pressBack()
        Thread.sleep(3000)

        //menuju fragment team
        Espresso.onView(ViewMatchers.withId(R.id.team_menu)).perform(ViewActions.click())
        Thread.sleep(3000)

        //cek recycler team
        Espresso.onView(ViewMatchers.withId(R.id.recycler_team)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //cek spinner team
        Espresso.onView(ViewMatchers.withId(R.id.spinner_team)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //click spinner team dan menuju liga yg telah ditentukan
        Espresso.onView(ViewMatchers.withId(R.id.spinner_team)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText("Spanish La Liga")).perform(ViewActions.click())
        Thread.sleep(3000)

        //cek swipe refresh
        Espresso.onView(ViewMatchers.withId(R.id.swipeRefresh_team)).perform(ViewActions.swipeDown())
        Thread.sleep(3000)

        //scroll dan click recycler pada posisi 7
        Espresso.onView(ViewMatchers.withId(R.id.recycler_team)).perform(RecyclerViewActions.scrollToPosition<AdapterTeamList.TeamListViewHolder>(7))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_team)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterTeamList.TeamListViewHolder>(7, ViewActions.click()))
        Thread.sleep(3000)

        //tambahkan ke favorite
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(3000)

        //cek recycler player yg terdapat dalam detail team
        Espresso.onView(ViewMatchers.withId(R.id.recycler_player)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //scroll dan click recycler player pada posisi 3
        Espresso.onView(ViewMatchers.withId(R.id.recycler_player)).perform(RecyclerViewActions.scrollToPosition<AdapterPlayerList.PlayerViewHolder>(3))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_player)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterPlayerList.PlayerViewHolder>(3, ViewActions.click()))
        Thread.sleep(3000)

        //kembali dari fragment player
        Espresso.pressBack()
        Thread.sleep(3000)

        //kembali dari activity team
        Espresso.pressBack()
        Thread.sleep(3000)

        //click bottomnavigation favorite
        Espresso.onView(ViewMatchers.withId(R.id.favorite_menu)).perform(ViewActions.click())
        Thread.sleep(3000)

        //cek tablayout favorite
        Espresso.onView(ViewMatchers.withId(R.id.tab_favorite)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //cek recycler favorite_match
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteMatch)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //scroll dan click pada recycler favorite match pada posisi 1
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteMatch)).perform(RecyclerViewActions.scrollToPosition<AdapterFavoriteEventList.FavoriteEventViewHolder>(1))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterFavoriteEventList.FavoriteEventViewHolder>(1, ViewActions.click()))
        Thread.sleep(3000)

        //tambahkan ke favorite database
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(3000)

        //kembali ke fragment favorite match
        Espresso.pressBack()
        Thread.sleep(3000)

        //swope left menuju recycler favorite team
        Espresso.onView(ViewMatchers.withId(R.id.viewPager_favorite)).perform(ViewActions.swipeLeft())
        Thread.sleep(3000)

        //cek recycler favorite team
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteTeam)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Thread.sleep(3000)

        //scroll dan click pada recyler favorite team posisi 1
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteTeam)).perform(RecyclerViewActions.scrollToPosition<AdapterFavoriteTeamList.FavoriteTeamViewHolder>(1))
        Espresso.onView(ViewMatchers.withId(R.id.recycler_favoriteTeam)).perform(RecyclerViewActions.actionOnItemAtPosition<AdapterFavoriteTeamList.FavoriteTeamViewHolder>(1, ViewActions.click()))
        Thread.sleep(3000)

        //tambahkan favorite
        Espresso.onView(ViewMatchers.withId(R.id.add_to_favorite)).perform(ViewActions.click())
        Thread.sleep(3000)

        //keluar dari detail favorite team
        Espresso.pressBack()
        Thread.sleep(3000)

    }
}