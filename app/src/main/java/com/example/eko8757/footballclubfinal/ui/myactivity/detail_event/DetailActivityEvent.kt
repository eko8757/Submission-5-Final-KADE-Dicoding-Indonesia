package com.example.eko8757.footballclubfinal.ui.myactivity.detail_event

import android.content.Intent
import android.database.sqlite.SQLiteConstraintException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.db.DBEvent
import com.example.eko8757.footballclubfinal.db.database
import com.example.eko8757.footballclubfinal.model.DetailEvent
import com.example.eko8757.footballclubfinal.model.Team
import com.example.eko8757.footballclubfinal.presenter.DetailPresenterEvent
import com.example.eko8757.footballclubfinal.ui.myactivity.MainActivity
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_event.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class DetailActivityEvent : AppCompatActivity(), DetailViewEvent {

    private var idEvent: String = ""
    private var idHomeTeam: String = ""
    private var idAwayTeam: String = ""
    private lateinit var presenter: DetailPresenterEvent
    private lateinit var events: DetailEvent
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_event)
        supportActionBar?.title = "Detail Event"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        back_arrow_detail_event.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        val intent = intent
        idEvent = intent.getStringExtra("idEvent")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenterEvent(this, request, gson)

        Log.d("idEvent", idEvent)
        presenter.getDetailEvent(idEvent)
    }

    override fun showTeam(data: Team) {
        when (data.teamId) {
            idHomeTeam -> {
                Picasso.get().load(data.teamBadge).into(homeBadge)
            }
            idAwayTeam -> {
                Picasso.get().load(data.teamBadge).into(awayBadge)
            }
        }
    }

    override fun getDetailEvent(data: DetailEvent) {
        events = DetailEvent(data.eventId, data.event, data.league, data.homeTeam, data.awayTeam,
                data.homeScore, data.awayScore, data.homeTeamId, data.awayTeamId, data.homeGoalDetail,
                data.awayGoalDetail, data.homeRedCards, data.homeYellowCards, data.awayRedCards, data.awayYellowCards, data.dateEvent)
        idHomeTeam = data.homeTeamId.toString()
        idAwayTeam = data.awayTeamId.toString()

        when (data.eventId) {
            idEvent -> {

                eventName.text = data.event
                leagueName.text = data.league
                homeTeam.text = data.homeTeam
                awayTeam.text = data.awayTeam
                home_score.text = data.homeScore
                awayScore.text = data.awayScore
                goalHomeDetail.text = data.homeGoalDetail
                goalAwayDetail.text = data.awayGoalDetail
                homeRedCardDetail.text = data.homeRedCards
                awayRedCardDetail.text = data.awayRedCards
                homeYellowCardDetail.text = data.homeYellowCards
                awayYellowCardDetail.text = data.awayYellowCards
                vs.text = "VS"
            }
        }
        presenter.getTeam(idHomeTeam)
        presenter.getTeam(idAwayTeam)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        menuItem = menu
        favoriteState()
        setFavorite()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) {
                    removeFromFavorite()
                } else {
                    addToFavorite()
                }
                isFavorite = !isFavorite
                setFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite() {

        val TAG: String = "TAG1"
        try {
            database.use {
                insert(DBEvent.TABLE_EVENT,
                        DBEvent.EVENT_ID to events.eventId,
                        DBEvent.HOMETEAM_ID to events.homeTeamId,
                        DBEvent.HOMETEAM to events.homeTeam,
                        DBEvent.AWAYTEAM_ID to events.awayTeamId,
                        DBEvent.AWAYTEAM to events.awayTeam,
                        DBEvent.HOMESCORE to events.homeScore,
                        DBEvent.AWAYSCORE to events.awayScore,
                        DBEvent.DATEEVENT to events.dateEvent)
            }
            toast("Added to Favorite")

            Log.d(TAG, events.homeTeam)
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(DBEvent.TABLE_EVENT,
                        "(EVENT_ID = {eventId})", "eventId" to idEvent)
            }
            toast("Delete Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border_black_24dp)
    }

    private fun favoriteState() {
        database.use {
            val result = select(DBEvent.TABLE_EVENT).whereArgs("(EVENT_ID = {id})",
                    "id" to idEvent)
            val favorite = result.parseList(classParser<DBEvent>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        finish()
    }
}
