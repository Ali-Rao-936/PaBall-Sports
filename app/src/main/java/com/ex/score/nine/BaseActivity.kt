package com.ex.score.nine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.getTeamsOrPlayersInSP
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.saveTeamsOrPlayersInSP
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: DataStore<Preferences>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        val teams_cont=findViewById<RelativeLayout>(R.id.teams_cont)
        val teams_radio=findViewById<RelativeLayout>(R.id.teams_radio)
        val players_cont=findViewById<RelativeLayout>(R.id.players_cont)
        val players_radio=findViewById<RelativeLayout>(R.id.players_radio)

        //fill radio button in case not selected yet or selected teams
        if (!getTeamsOrPlayersInSP(applicationContext).isEmpty()
            &&
            (getTeamsOrPlayersInSP(applicationContext).equals("empty")
                    || getTeamsOrPlayersInSP(applicationContext).equals("teams")))
        {
            teams_radio.visibility= View.VISIBLE
        }else{
            players_radio.visibility= View.VISIBLE
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


    }
}