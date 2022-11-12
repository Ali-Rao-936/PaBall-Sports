package com.ex.score.nine.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.databinding.ActivitySplashBinding
import com.ex.score.nine.domain.models.*
import com.ex.score.nine.domain.models.lineup.Lineup
import com.ex.score.nine.domain.models.lineup.Players
import com.ex.score.nine.presentation.sharedPreferences.SharedPreference
import com.ex.score.nine.utils.Constants
import com.ex.score.nine.utils.Utils
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreen : BaseActivity() {

    @Inject
    lateinit var viewModel: HomeViewModel

    lateinit var binding: ActivitySplashBinding

    val TAG: String = "SplashScreen"
    private var pageNumber = 1
    private var pageCount = 0
    private var loginStatus: Boolean = false

    companion object {
        var matchesList = ArrayList<Match>()
        var suggestionPlayersList = ArrayList<String>()
        var suggestionTeamsList = ArrayList<String>()
        var playersList = ArrayList<PlayerBio>()
        var teamsList = ArrayList<TeamInfo>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*   val locale = Locale(
          SharedPreference.getInstance().getStringValueFromPreference(
              SharedPreference.LOCALE_KEY,
              SharedPreference.ENGLISH,this))

      val configuration = Configuration()
      configuration.setLocale(locale)
      configuration.setLayoutDirection(locale)*/
        val localLocale = Locale.getDefault().language
        if (localLocale.contains("zh")) {
            Utils.setLocale(applicationContext, SharedPreference.CHINESE)
        }
        val languageToLoad = SharedPreference.getInstance().getStringValueFromPreference(
            SharedPreference.LOCALE_KEY,
            SharedPreference.ENGLISH, this
        ) // your language
        val config = applicationContext.resources.configuration
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            applicationContext.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        initObserver()

        // for players
        lifecycleScope.launch {
            viewModel.getPlayers()
        }
    }

    private fun goToNext() {
        lifecycleScope.launch {
            loginStatus =
                Utils.getBooleanValue(preferences, Constants.IS_FIRST_TIME).first() ?: true

            val intent = if (loginStatus) {
                Intent(this@SplashScreen, OnboardingActivity::class.java)
            } else {
                Intent(this@SplashScreen, MainActivity::class.java)
            }
            startActivity(intent)
            finish()


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
                if (homeBackup.playerPhoto.isNotEmpty() && homeBackup.playerName.isNotEmpty() && homeBackup.playerHeight.isNotEmpty() && homeBackup.playerCountry.isNotEmpty()) {
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
                if (homeLineUp.playerPhoto.isNotEmpty() && homeLineUp.playerName.isNotEmpty() && homeLineUp.playerHeight.isNotEmpty() && homeLineUp.playerCountry.isNotEmpty()) {
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
                if (awayBackup.playerPhoto.isNotEmpty() && awayBackup.playerName.isNotEmpty() && awayBackup.playerHeight.isNotEmpty() && awayBackup.playerCountry.isNotEmpty()) {
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
                if (awayLineUp.playerPhoto.isNotEmpty() && awayLineUp.playerName.isNotEmpty() && awayLineUp.playerHeight.isNotEmpty() && awayLineUp.playerCountry.isNotEmpty()) {
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

        // for teams
        lifecycleScope.launch {
            viewModel.getMatches(Utils.getLocale(preferences), pageNumber.toString())

        }
        //       println(playersList[0].name)
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

        if (teamsList.size < 50)
            loadMoreMatches()
        else
            goToNext()

//        for (team in teamsList) {
//            println(team.currentLocation)
//        }

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
        showToast(message)
    }

    private fun handleException(message: String) {
        Log.d(TAG, "exception    $message")
        showToast(message)
    }


    private fun showToast(s: String) {

        Snackbar.make(binding.root, s, Snackbar.LENGTH_LONG).show()

    }

}