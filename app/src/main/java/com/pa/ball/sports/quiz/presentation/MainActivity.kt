package com.pa.ball.sports.quiz.presentation

import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.pa.ball.sports.quiz.BaseActivity
import com.pa.ball.sports.quiz.R
import com.pa.ball.sports.quiz.data.ListResponse
import com.pa.ball.sports.quiz.data.ListResponse.adsArrayList
import com.pa.ball.sports.quiz.domain.models.Ads
import com.pa.ball.sports.quiz.domain.models.Scores
import com.pa.ball.sports.quiz.presentation.adapters.AdapterTopScore
import com.pa.ball.sports.quiz.presentation.adapters.ViewPager2Adapter
import com.pa.ball.sports.quiz.presentation.quiz.QuizActivity
import com.pa.ball.sports.quiz.presentation.sharedPreferences.Functions.fillSoccer
import com.pa.ball.sports.quiz.presentation.sharedPreferences.Functions.showPopupMessageCheck
import com.pa.ball.sports.quiz.presentation.sharedPreferences.SharedPreference
import com.pa.ball.sports.quiz.presentation.sharedPreferences.TeamsOrPlayers.getTeamsOrPlayersInSP
import com.pa.ball.sports.quiz.presentation.sharedPreferences.TeamsOrPlayers.saveTeamsOrPlayersInSP
import com.pa.ball.sports.quiz.utils.GeneralTools
import com.pa.ball.sports.quiz.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), ViewPager2Adapter.PassAdsDetails {

    @Inject
    lateinit var viewModel: HomeViewModel

    var search_edt: EditText? = null
    var cancel_button_rl: RelativeLayout? = null
    var scoresArrayList = ArrayList<Scores>()
    var adapterTopScore: AdapterTopScore? = null
    var teams_or_players: String? = null

    var viewPager2: ViewPager2? = null
    var relativeLayout_con_viewPager2: RelativeLayout? = null
    var viewPager2Adapter: ViewPager2Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_main)


        //Casting
        val teams_cont = findViewById<RelativeLayout>(R.id.teams_cont)
        val teams_radio = findViewById<RelativeLayout>(R.id.teams_radio)
        val players_cont = findViewById<RelativeLayout>(R.id.players_cont)
        val players_radio = findViewById<RelativeLayout>(R.id.players_radio)
        val recycler_view = findViewById<RecyclerView>(R.id.recycler_view)
        val play_game_rl = findViewById<RelativeLayout>(R.id.play_game_rl)
        search_edt = findViewById<EditText>(R.id.search_edt)
        cancel_button_rl = findViewById<RelativeLayout>(R.id.cancel_button_rl)
        viewPager2 = findViewById<ViewPager2>(R.id.viewpager)
        relativeLayout_con_viewPager2 = findViewById<RelativeLayout>(R.id.view2)

        findViewById<ImageView>(R.id.ivLanguages).setOnClickListener {
            showDialogForLanguages()
        }

        showPopup()

        handelSlider()


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
        adapterTopScore =
            AdapterTopScore(
                applicationContext,
                scoresArrayList
            )
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
                        showDialogWebView(i.getMap_link())
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

     fun showPopup() {
        if (showPopupMessageCheck(this))
            GeneralTools.messageDialog(this)

//        if (!getPromptFrequencyFromSP(this).equals("empty") && !getPromptFrequencyFromSP(this).equals("done")&& !getPromptFrequencyFromSP(this).equals("0")
//        ) {
//            Utils.messageDialog(this)
//        }
    }

    private fun filter(text: String) {
        val scoresAfterFilterArrayList = ArrayList<Scores>()
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
        cancel_button_rl?.setOnClickListener({ search_edt?.setText("") })
    }

    private fun makeCancelTitleIVVISIBLE() {
        cancel_button_rl?.visibility = View.VISIBLE
    }

    private fun makeCancelTitleIVGONE() {
        cancel_button_rl?.setVisibility(View.GONE)
    }

    private fun handelSlider() {
        if (!adsArrayList.isNullOrEmpty()) {
            viewPager2Adapter =
                ViewPager2Adapter(
                    this@MainActivity,
                    this
                )
            viewPager2!!.adapter = viewPager2Adapter
            viewPager2?.clipToPadding = false
            viewPager2?.clipChildren = false
            viewPager2?.offscreenPageLimit = 1
            viewPager2?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            infintLoop()
            viewPager2?.isUserInputEnabled = false
        } else {
            relativeLayout_con_viewPager2?.visibility = View.GONE
        }

    }

    private fun infintLoop() {
        Handler().postDelayed({ moveSecondPage() }, 3000)
    }

    private fun moveSecondPage() {
        if (viewPager2!!.currentItem == 0) {
            viewPager2?.currentItem = 1
        } else {
            viewPager2?.currentItem = 0
        }
        infintLoop()
    }

    override fun onClickedAdsDetails(adsDetails: Ads?) {
        if (adsDetails!!.open_type.equals("1")) {
            showDialogWebView(adsDetails.redirect_url)//keep error
        }
    }

    private fun showDialogForLanguages() {
        var shouldRefresh = false
        val dialog = Dialog(this, android.R.style.ThemeOverlay)
        dialog.setContentView(R.layout.language_dialog)
        val english = dialog.findViewById<View>(R.id.english)
        val chinese = dialog.findViewById<View>(R.id.chinese)
        val indonesian = dialog.findViewById<View>(R.id.indonesian)
        val vietnam = dialog.findViewById<View>(R.id.vietnamese)
        val thai = dialog.findViewById<View>(R.id.thai)

        val ivEnglish = dialog.findViewById<ImageView>(R.id.ivEnglishCheckBox)
        val ivChinese = dialog.findViewById<ImageView>(R.id.ivChineseCheckBox)
        val ivIndonesian = dialog.findViewById<ImageView>(R.id.ivIndonesiaCheckBox)
        val ivVietNames = dialog.findViewById<ImageView>(R.id.ivVietNamesCheckBox)
        val ivThai = dialog.findViewById<ImageView>(R.id.ivThaiCheckBox)

        dialog.findViewById<View>(R.id.back_btn).setOnClickListener {
            dialog.dismiss()
            if (shouldRefresh) {
               startActivity(Intent(this, SplashScreen::class.java))
                finish()
            }
        }

        fun toggleSelected() {

            ivEnglish.setImageResource(R.drawable.lang_un_checkbox)
            ivChinese.setImageResource(R.drawable.lang_un_checkbox)
            ivIndonesian.setImageResource(R.drawable.lang_un_checkbox)
            ivVietNames.setImageResource(R.drawable.lang_un_checkbox)
            ivThai.setImageResource(R.drawable.lang_un_checkbox)

            when (Utils.getLocale(this)) {
                SharedPreference.ENGLISH -> {
                    ivEnglish.setImageResource(R.drawable.lang_checkedbox)
                }
                SharedPreference.CHINESE -> {
                    ivChinese.setImageResource(R.drawable.lang_checkedbox)
                }
                SharedPreference.INDONESIAN -> {
                    ivIndonesian.setImageResource(R.drawable.lang_checkedbox)
                }
                SharedPreference.VIETNAMESE -> {
                    ivVietNames.setImageResource(R.drawable.lang_checkedbox)
                }
                SharedPreference.THAI -> {
                    ivThai.setImageResource(R.drawable.lang_checkedbox)
                }
            }
        }

        toggleSelected()

        english.setOnClickListener {
            Utils.setLocale(this, SharedPreference.ENGLISH)
            shouldRefresh = true
            toggleSelected()
        }
        chinese.setOnClickListener {
            Utils.setLocale(this, SharedPreference.CHINESE)
            shouldRefresh = true
            toggleSelected()
        }
        indonesian.setOnClickListener {
            Utils.setLocale(this, SharedPreference.INDONESIAN)
            shouldRefresh = true
            toggleSelected()
        }
        vietnam.setOnClickListener {
            Utils.setLocale(this, SharedPreference.VIETNAMESE)
            shouldRefresh = true
            toggleSelected()
        }
        thai.setOnClickListener {
            Utils.setLocale(this, SharedPreference.THAI)
            shouldRefresh = true
            toggleSelected()
        }

        dialog.show()
        dialog.setCancelable(false)
    }

    public fun showDialogWebView(url: String) {
        var shouldRefresh = false
        val dialog = Dialog(this, android.R.style.ThemeOverlay)
        dialog.setContentView(R.layout.web_view_dialog)

        val web_view = dialog.findViewById<WebView>(R.id.web_vew)

//        Log.i("TAG","getUrlFromSP(this): "+getUrlFromSP(this))
//getUrlFromSP(this)
        web_view.setWebViewClient(WebViewClient())
        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(url)

        dialog.findViewById<View>(R.id.back_btn_rl_web_view).setOnClickListener {
            dialog.dismiss()
            if (shouldRefresh)
                recreate()
        }

        dialog.show()
        dialog.setCancelable(false)
    }

}