package com.example.eko8757.footballclubfinal.ui.myactivity.detail_team

import android.database.sqlite.SQLiteConstraintException
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.adapter.AdapterPlayerList
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.db.DBTeam
import com.example.eko8757.footballclubfinal.db.database
import com.example.eko8757.footballclubfinal.model.Player
import com.example.eko8757.footballclubfinal.model.Team
import com.example.eko8757.footballclubfinal.presenter.DetailPresenterTeam
import com.example.eko8757.footballclubfinal.ui.myactivity.detail_player.DetailActivityPlayer
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_team.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class DetailActivityTeam : AppCompatActivity(), DetailViewTeam {

    private var idTeam: String = ""
    private var player: MutableList<Player> = mutableListOf()
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    private lateinit var team: Team
    private lateinit var presenter: DetailPresenterTeam
    private lateinit var adapter: AdapterPlayerList
    private lateinit var playerList: RecyclerView


    override fun getDetailTeam(data: Team) {
        team = Team(data.teamId, data.teamName, data.teamBadge)
        when (data.teamId) {
            idTeam -> {
                Picasso.get().load(data.teamBadge).into(logo_team)
                club_team.text = data.teamName
                club_year_formed_team.text = data.teamFormedYear
                club_stadium_team.text = data.teamStadium
                overview_team.text = data.teamDescription
                playerList = findViewById(R.id.recycler_player)

                playerList.layoutManager = LinearLayoutManager(this)
                adapter = AdapterPlayerList(player) {
                    startActivity<DetailActivityPlayer>("idPlayer" to "${it.playerId}")
                }
                playerList.adapter = adapter
                presenter.getPlayerTeam(idTeam)
            }
        }
    }

    override fun showPlayer(data: List<Player>) {
        player.clear()
        player.addAll(data)
        adapter.notifyDataSetChanged()
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
                insert(DBTeam.TABLE_TEAMS,
                        DBTeam.TEAMS_ID to team.teamId,
                        DBTeam.TEAMS_NAME to team.teamName,
                        DBTeam.BADGE_TEAM to team.teamBadge
                )
            }
            toast("Added to Favorite")

            Log.d(TAG, team.teamName)
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun removeFromFavorite() {
        try {
            database.use {
                delete(DBTeam.TABLE_TEAMS, "(TEAMS_ID = {teamId})", "teamId" to idTeam)
            }
            toast("Delete Favorite")
        } catch (e: SQLiteConstraintException) {
            toast(e.localizedMessage)
        }
    }

    private fun setFavorite() {
        if (isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.star)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.favorites)
    }

    private fun favoriteState() {
        database.use {
            val result = select(DBTeam.TABLE_TEAMS).whereArgs("(TEAMS_ID = {id})", "id" to idTeam)
            val favorite = result.parseList(classParser<DBTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_team)

        supportActionBar?.title = resources.getText(R.string.detailteam)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idTeam = intent?.getStringExtra("idTeam").toString()
        Log.d(idTeam, "idTeam")

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenterTeam(this, request, gson)

        presenter.getDetailTeam(idTeam)
    }
}

