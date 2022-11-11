package com.ex.score.nine

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.score.nine.data.ListResponse.mapArrayList
import com.ex.score.nine.domain.models.Scores
import com.ex.score.nine.presenter.QuizActivity
import com.ex.score.nine.presenter.SplashScreen
import com.ex.score.nine.presenter.adapters.AdapterTopScore
import com.ex.score.nine.sharedPreferences.Functions.fillSoccer
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.getTeamsOrPlayersInSP
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers.saveTeamsOrPlayersInSP
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var preferences: DataStore<Preferences>
    var search_edt:EditText?=null
    var cancel_button_rl:RelativeLayout?=null
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
        search_edt=findViewById<EditText>(R.id.search_edt)
        cancel_button_rl=findViewById<RelativeLayout>(R.id.cancel_button_rl)

        actionListenerToRemoveTextInSearchEdt()

        //fill radio button in case not selected yet or selected teams
        if (!getTeamsOrPlayersInSP(applicationContext).isEmpty()
            &&
            (getTeamsOrPlayersInSP(applicationContext).equals("empty")
                    || getTeamsOrPlayersInSP(applicationContext).equals("teams")))
        {
            teams_radio.visibility= View.VISIBLE
            teams_or_players = "teams"
        }else{
            players_radio.visibility= View.VISIBLE
            teams_or_players = "players"
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


        //search on edittext
        search_edt?.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(cs: CharSequence, arg1: Int, arg2: Int, arg3: Int) {
                if (cs.length != 0) makeCancelTitleIVVISIBLE() else makeCancelTitleIVGONE()
            }

            override fun beforeTextChanged(s: CharSequence, arg1: Int, arg2: Int, arg3: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
                for (i in mapArrayList){
                    if (search_edt?.editableText.toString().equals(i.getMap_key()))
                    {
                        //showDialogWebView(i.getMap_link())//keep error
                    }
                }
            }
        })


        //action listener to play game
        play_game_rl.setOnClickListener(View.OnClickListener {
           if (teams_or_players.equals("teams"))
           {
               Log.i("TAG","teams_or_players: "+teams_or_players)
           }else{

           }
            startActivity(Intent(this@BaseActivity, QuizActivity::class.java))


        })

    }


    private  fun filter(text: String) {
        val scoresAfterFilterArrayList = java.util.ArrayList<Scores>()
        for (scores in scoresArrayList) {
            if (scores.name.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                scoresAfterFilterArrayList.add(scores)
            }
        }
        if (scoresAfterFilterArrayList.isEmpty()) adapterTopScore!!.filterList(scoresArrayList) else adapterTopScore!!.filterList(
            scoresAfterFilterArrayList
        )
    }

    private  fun actionListenerToRemoveTextInSearchEdt() {
        cancel_button_rl?.setOnClickListener(View.OnClickListener { search_edt?.setText("") })
    }

    private  fun makeCancelTitleIVVISIBLE() {
        cancel_button_rl?.visibility=View.VISIBLE
    }

    private  fun makeCancelTitleIVGONE() {
        cancel_button_rl?.setVisibility(View.GONE)
    }
}