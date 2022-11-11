package com.ex.score.nine

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.score.nine.domain.models.Scores
import com.ex.score.nine.presenter.adapters.AdapterTopScore
import com.ex.score.nine.sharedPreferences.Functions.fillSoccer
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.getTeamsOrPlayersInSP
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.saveTeamsOrPlayersInSP
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: DataStore<Preferences>

    //String contaxt_str,url;
    var scoresArrayList: ArrayList<Scores> = ArrayList<Scores>()
    var adapterTopScore: AdapterTopScore? = null
    var teams_or_players:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        //Casting
        val teams_cont=findViewById<RelativeLayout>(R.id.teams_cont)
        val teams_radio=findViewById<RelativeLayout>(R.id.teams_radio)
        val players_cont=findViewById<RelativeLayout>(R.id.players_cont)
        val players_radio=findViewById<RelativeLayout>(R.id.players_radio)
        val recycler_view=findViewById<RecyclerView>(R.id.recycler_view)
        val play_game_rl=findViewById<RelativeLayout>(R.id.play_game_rl)


        //fill radio button in case not selected yet or selected teams
        if (!getTeamsOrPlayersInSP(applicationContext).isEmpty()
            &&
            (getTeamsOrPlayersInSP(applicationContext).equals("empty")
                    || getTeamsOrPlayersInSP(applicationContext).equals("teams")))
        {
            teams_radio.visibility= View.VISIBLE
            teams_or_players = "teams"
            Log.i("TAG","teams_or_players: "+teams_or_players)
        }else{
            players_radio.visibility= View.VISIBLE
            teams_or_players = "players"
            Log.i("TAG","teams_or_players: "+teams_or_players)
        }




        //action listener to teams and players
        teams_cont.setOnClickListener(View.OnClickListener {
            teams_radio.visibility=View.VISIBLE
            players_radio.visibility=View.GONE
            saveTeamsOrPlayersInSP(applicationContext,"teams")
        })

        players_cont.setOnClickListener(View.OnClickListener {
            teams_radio.visibility=View.GONE
            players_radio.visibility=View.VISIBLE
            saveTeamsOrPlayersInSP(applicationContext,"players")
        })




        //create score list
        scoresArrayList = fillSoccer()

        recycler_view.setHasFixedSize(true)
        val mLayoutManager = GridLayoutManager(applicationContext, 1)
        recycler_view.setLayoutManager(mLayoutManager)
        adapterTopScore = AdapterTopScore(applicationContext, scoresArrayList)
        recycler_view.setAdapter(adapterTopScore)




        //action listener to play game
        play_game_rl.setOnClickListener(View.OnClickListener {
           if (teams_or_players.equals("teams"))
           {
               Log.i("TAG","teams_or_players: "+teams_or_players)
           }else{

           }

        })

    }
}