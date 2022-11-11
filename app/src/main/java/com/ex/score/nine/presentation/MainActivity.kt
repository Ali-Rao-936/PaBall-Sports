package com.ex.score.nine.presentation

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.data.ListResponse
import com.ex.score.nine.databinding.ActivityMainBinding
import com.ex.score.nine.domain.models.*
import com.ex.score.nine.domain.models.lineup.Lineup
import com.ex.score.nine.domain.models.lineup.Players
import com.ex.score.nine.presentation.adapters.AdapterTopScore
import com.ex.score.nine.presentation.quiz.QuizActivity
import com.ex.score.nine.presentation.sharedPreferences.Functions.fillSoccer
import com.ex.score.nine.presentation.sharedPreferences.TeamsOrPlayers.getTeamsOrPlayersInSP
import com.ex.score.nine.presentation.sharedPreferences.TeamsOrPlayers.saveTeamsOrPlayersInSP
import com.ex.score.nine.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    lateinit var binding: ActivityMainBinding
    val TAG: String = "MainActivity"
    var pageNumber = 1
    var pageCount = 0

    var search_edt: EditText? = null
    var cancel_button_rl: RelativeLayout? = null
    var scoresArrayList = ArrayList<Scores>()
    var adapterTopScore: AdapterTopScore? = null
    var teams_or_players: String? = null

    companion object {
        var matchesList = ArrayList<Match>()
        var suggestionPlayersList = ArrayList<String>()
        var suggestionTeamsList = ArrayList<String>()
        var playersList = ArrayList<PlayerBio>()
        var teamsList = ArrayList<TeamInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initObserver()

        //Casting
        val teams_cont = findViewById<RelativeLayout>(R.id.teams_cont)
        val teams_radio = findViewById<RelativeLayout>(R.id.teams_radio)
        val players_cont = findViewById<RelativeLayout>(R.id.players_cont)
        val players_radio = findViewById<RelativeLayout>(R.id.players_radio)
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        val play_game_rl = findViewById<RelativeLayout>(R.id.play_game_rl)
        search_edt = findViewById<EditText>(R.id.search_edt)
        cancel_button_rl = findViewById<RelativeLayout>(R.id.cancel_button_rl)

        // for teams
        lifecycleScope.launch {
               viewModel.getMatches(Utils.getLocale(preferences), pageNumber.toString())

        }
        // for players
      //  viewModel.getPlayers()

        actionListenerToRemoveTextInSearchEdt()

        //fill radio button in case not selected yet or selected teams
        if (!getTeamsOrPlayersInSP(applicationContext).isEmpty()
            &&
            (getTeamsOrPlayersInSP(applicationContext).equals("empty")
                    || getTeamsOrPlayersInSP(applicationContext).equals("teams"))
        ) {
            teams_radio.visibility = View.VISIBLE
            teams_or_players = "teams"
        } else {
            players_radio.visibility = View.VISIBLE
            teams_or_players = "players"
        }


        //action listener to teams and players
        teams_cont.setOnClickListener(View.OnClickListener {
            teams_radio.visibility = View.VISIBLE
            players_radio.visibility = View.GONE
            saveTeamsOrPlayersInSP(applicationContext, "teams")
        })

        players_cont.setOnClickListener(View.OnClickListener {
            teams_radio.visibility = View.GONE
            players_radio.visibility = View.VISIBLE
            saveTeamsOrPlayersInSP(applicationContext, "players")
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
                for (i in ListResponse.mapArrayList) {
                    if (search_edt?.editableText.toString().equals(i.getMap_key())) {
                        //showDialogWebView(i.getMap_link())//keep error
                    }
                }
            }
        })


        //action listener to play game
        play_game_rl.setOnClickListener(View.OnClickListener {
            if (teams_or_players.equals("teams")) {
                Log.i("TAG", "teams_or_players: " + teams_or_players)
            } else {

            }
            startActivity(Intent(this, QuizActivity::class.java))


        })

    }


    private fun filter(text: String) {
        val scoresAfterFilterArrayList = java.util.ArrayList<Scores>()
        for (scores in scoresArrayList) {
            if (scores.name!!.toLowerCase().contains(text.lowercase(Locale.getDefault()))) {
                scoresAfterFilterArrayList.add(scores)
            }
        }
        if (scoresAfterFilterArrayList.isEmpty()) adapterTopScore!!.filterList(scoresArrayList) else adapterTopScore!!.filterList(
            scoresAfterFilterArrayList
        )
    }

    private fun actionListenerToRemoveTextInSearchEdt() {
        cancel_button_rl?.setOnClickListener(View.OnClickListener { search_edt?.setText("") })
    }

    private fun makeCancelTitleIVVISIBLE() {
        cancel_button_rl?.visibility = View.VISIBLE
    }

    private fun makeCancelTitleIVGONE() {
        cancel_button_rl?.setVisibility(View.GONE)
    }

    private fun initObserver() {
        viewModel.mState.flowWithLifecycle(
            this.lifecycle, Lifecycle.State.STARTED
        ).onEach {
            handleState(it)
        }.launchIn(this.lifecycleScope)
    }

    private fun handleState(state: HomeStateModel) {
        when (state) {
            is HomeStateModel.IsLoading -> handleIsLoadingState(state.isLoading)
            is HomeStateModel.Response -> handleTeamResponse(state.videos)
            is HomeStateModel.PlayersResponse -> handlePlayersResponse(state.players)
            is HomeStateModel.NoInternetException -> handleNetworkFailure(state.message)
            is HomeStateModel.GeneralException -> handleException(state.message)
            is HomeStateModel.StatusFailed -> handleFailure(state.message)
            else -> {
                Log.d(TAG, " no state run ")
            }
        }
    }

    private fun handlePlayersResponse(response: Players) {
        Log.d(TAG, " response success  " + response.lineupList.count())
        handlePlayersData(response.lineupList)
    }


    private fun handleTeamResponse(response: BaseClassIndexNew) {
        Log.d(TAG, " response success  " + response.matchList.count())
        pageCount = response.meta.total
        matchesList.addAll(response.matchList)
        handleTeamsData(matchesList)
    }

    private fun handlePlayersData(lineupList: List<Lineup>) {
        for (data in lineupList) {
            // we have 4 kinds of list in response each list contains player info
            for (homeBackup in data.homeBackup) {
                // if player have photo
                if (homeBackup.playerPhoto.isNotEmpty()) {
                    playersList.add(
                        PlayerBio(
                            homeBackup.playerName,
                            homeBackup.playerHeight,
                            homeBackup.playerCountry,
                            homeBackup.playerPhoto
                        )
                    )
                }
                //  add players names for suggestions
                if (!suggestionPlayersList.contains(homeBackup.playerName))
                    suggestionPlayersList.add(homeBackup.playerName)
            }

            for (homeLineUp in data.homeLineup) {
                // if player have photo
                if (homeLineUp.playerPhoto.isNotEmpty()) {
                    playersList.add(
                        PlayerBio(
                            homeLineUp.playerName,
                            homeLineUp.playerHeight,
                            homeLineUp.playerCountry,
                            homeLineUp.playerPhoto
                        )
                    )
                }
                //  add players names for suggestions
                if (!suggestionPlayersList.contains(homeLineUp.playerName))
                    suggestionPlayersList.add(homeLineUp.playerName)
            }

            for (awayBackup in data.awayBackup) {
                // if player have photo
                if (awayBackup.playerPhoto.isNotEmpty()) {
                    playersList.add(
                        PlayerBio(
                            awayBackup.playerName,
                            awayBackup.playerHeight,
                            awayBackup.playerCountry,
                            awayBackup.playerPhoto
                        )
                    )
                }
                //  add players names for suggestions
                if (!suggestionPlayersList.contains(awayBackup.playerName))
                    suggestionPlayersList.add(awayBackup.playerName)
            }

            for (awayLineUp in data.awayLineup) {
                // if player have photo
                if (awayLineUp.playerPhoto.isNotEmpty()) {
                    playersList.add(
                        PlayerBio(
                            awayLineUp.playerName,
                            awayLineUp.playerHeight,
                            awayLineUp.playerCountry,
                            awayLineUp.playerPhoto
                        )
                    )
                }
                //  add players names for suggestions
                if (!suggestionPlayersList.contains(awayLineUp.playerName))
                    suggestionPlayersList.add(awayLineUp.playerName)
            }


        }

        println(playersList[0].name)
//       for (image in playersList){
//           println(image.photoUrl)
//       }
//            println(suggestionPlayersList)


    }


    private fun handleTeamsData(matchesList: ArrayList<Match>) {
        for (match in matchesList) {
            if (match.homeName.isNotEmpty() && match.leagueName.isNotEmpty() && match.homeLogo.isNotEmpty() && match.location.isNotEmpty()) {
                teamsList.add(
                    TeamInfo(
                        match.homeName,
                        match.leagueName,
                        match.homeLogo,
                        match.location
                    )
                )
            }

            if (match.homeName.isNotEmpty()) {
                if (!suggestionTeamsList.contains(match.homeName))
                    suggestionTeamsList.add(match.homeName)
            }
        }

        if (teamsList.size < 70)
            loadMoreMatches()

        for (team in teamsList) {
            println(team.homeName)
        }

        //     println(suggestionTeamsList)

    }

    // pagination
    private fun loadMoreMatches() {
        if (pageNumber > pageCount)
            return
        else {
            pageNumber += 1
            lifecycleScope.launch {
                viewModel.getMatches(Utils.getLocale(preferences), pageNumber.toString())

            }
        }

    }

    private fun handleIsLoadingState(loading: Boolean) {
        if (loading) {
            Log.d(TAG, "show loader....")
        } else {
            Log.d(TAG, "..... stop loader")
        }
    }

    private fun handleFailure(message: String) {
        Log.d(TAG, "failure    $message")
        showToast(message)
    }


    private fun handleNetworkFailure(message: String) {
        Log.d(TAG, "network    $message")
    }

    private fun handleException(message: String) {
        Log.d(TAG, "exception    $message")
        showToast(message)
    }


    private fun showToast(s: String) {
        Toast.makeText(
            this,
            s,
            Toast.LENGTH_SHORT
        )
            .show()
    }

}