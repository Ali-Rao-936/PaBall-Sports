package com.ex.score.nine.presentation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.domain.models.BaseClassIndexNew
import com.ex.score.nine.domain.models.Match
import com.ex.score.nine.domain.models.PlayerBio
import com.ex.score.nine.domain.models.TeamInfo
import com.ex.score.nine.domain.models.lineup.Lineup
import com.ex.score.nine.domain.models.lineup.Players
import com.ex.score.nine.presentation.quiz.RewardActivity
import com.ex.score.nine.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel
    val TAG: String = "MainActivity"
    var pageNumber = 1
    var pageCount = 0

    companion object {
        var matchesList = ArrayList<Match>()
        var suggestionPlayersList = ArrayList<String>()
        var suggestionTeamsList = ArrayList<String>()
        var playersList = ArrayList<PlayerBio>()
        var teamsList = ArrayList<TeamInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initObserver()

        // for teams
        lifecycleScope.launch {
            viewModel.getMatches(Utils.getLocale(preferences), pageNumber.toString())

        }
        // for players
        //     viewModel.getPlayers()

        findViewById<ImageView>(R.id.ivLogoMain).setOnClickListener {
            startActivity(Intent(this, RewardActivity::class.java))
        }

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

        for (team in teamsList){
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