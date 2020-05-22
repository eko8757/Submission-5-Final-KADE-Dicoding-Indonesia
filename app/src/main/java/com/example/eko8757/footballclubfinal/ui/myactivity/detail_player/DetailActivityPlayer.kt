package com.example.eko8757.footballclubfinal.ui.myactivity.detail_player

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.eko8757.footballclubfinal.R
import com.example.eko8757.footballclubfinal.api.ApiRepository
import com.example.eko8757.footballclubfinal.model.Player
import com.example.eko8757.footballclubfinal.presenter.DetailPresenterPlayer
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_player.*

class DetailActivityPlayer : AppCompatActivity(), DetailViewPlayer {

    private var idPlayer: String = ""
    private lateinit var namePlayer: String
    private lateinit var presenter: DetailPresenterPlayer
    private lateinit var player: Player

    override fun getDetailPlayer(data: Player) {
        player = Player(data.playerId, data.strPlayer, data.strPosition, data.strCutout,data.strDescription,
                data.strThumb, data.strHeight, data.strWeight, data.strFanart1)
        Log.d("idPlayer", data.playerId)
        namePlayer = data.strPlayer.toString()
        when(data.playerId){
            idPlayer -> {
                Picasso.get().load(data.strThumb).into(image_player)
                weight_player.text = data.strWeight
                height_player.text = data.strHeight
                position_player.text = data.strPosition
                description_player.text = data.strDescription
            }
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_player)

        supportActionBar?.title = resources.getText(R.string.detailplayer)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val intent = intent
        idPlayer = intent.getStringExtra("idPlayer")
        Log.d("idPlayer", idPlayer)

        val request = ApiRepository()
        val gson = Gson()
        presenter = DetailPresenterPlayer(this, request, gson)

        presenter.getDetailPlayer(idPlayer)
    }
}
