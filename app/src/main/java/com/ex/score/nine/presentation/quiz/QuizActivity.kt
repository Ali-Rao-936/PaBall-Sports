package com.ex.score.nine.presentation.quiz

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.data.ListResponse
import com.ex.score.nine.domain.models.Ads
import com.ex.score.nine.domain.models.PlayerBio
import com.ex.score.nine.presentation.adapters.ViewPager2Adapter
import com.ex.score.nine.presentation.fragments.quiz_fragments.FragmentQuestion
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizActivity : BaseActivity() ,ViewPager2Adapter.PassAdsDetails {

    var questionCont: RelativeLayout? = null
    val gson = Gson()
    private lateinit var suggestionsList: ArrayList<String>
    lateinit var showPlayersList: ArrayList<PlayerBio>
    private var answersList = ArrayList<String>()
    private var currentIndex = 0
    private lateinit var correctAnswer: String
    private var savedQuestionsList = ArrayList<String>()

    lateinit var fragmentQuestion: FragmentQuestion

    var viewPager2: ViewPager2? = null
    var relativeLayout_con_viewPager2: RelativeLayout? = null
    var viewPager2Adapter: ViewPager2Adapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

//
//        suggestionsList = suggestionPlayersList
//        showPlayersList = playersList
//
//        // get alreadyAsked questionsList from preferences
//        lifecycleScope.launch {
//            val json =
//                Utils.getStringValue(preferences, Constants.SAVED_QUESTIONS).first() ?: "no data"
//            if (json != "no data") {
//                // below line is to get the type of our array list.
//                val type: Type = object : TypeToken<java.util.ArrayList<String?>?>() {}.type
//                savedQuestionsList = gson.fromJson<Any>(json, type) as java.util.ArrayList<String>
//                currentIndex = getIndex(showPlayersList, savedQuestionsList)
//            } else
//                currentIndex = 0
//
//            correctAnswer = showPlayersList[currentIndex].name
//            //generate random answers
//            answersList = generateAnswersList(correctAnswer, suggestionsList)
//            answersList.add(correctAnswer)
//
//            println(correctAnswer)
//            println(currentIndex)
//            println(answersList)
//            shuffle(answersList)
//            println(answersList)

        // when user answer is correct
//            val newPhotoUrl = showPlayersList[currentIndex].photoUrl
//            // save the question
//            savedQuestionsList.add(newPhotoUrl)
//
//            Utils.addStringValue(
//                preferences,
//                Constants.SAVED_QUESTIONS,
//                gson.toJson(savedQuestionsList)
//            )
//            // remove from current questions list
//            showPlayersList.removeAt(currentIndex)
//            currentIndex = getIndex(playersList, savedQuestionsList)
//            answersList.clear()
//            correctAnswer = showPlayersList[currentIndex].name
//            answersList = generateAnswersList(correctAnswer, suggestionsList)
//            answersList.add(correctAnswer)
//            shuffle(answersList)

        //     }
        statusBarColor()
        cast()
        handleQuestionFragment()
        handelSlider()
    }

    private fun handelSlider() {
        if (!ListResponse.adsArrayList.isNullOrEmpty())
        {
            viewPager2Adapter = ViewPager2Adapter(this@QuizActivity, this)
            viewPager2!!.adapter = viewPager2Adapter
            viewPager2?.clipToPadding = false
            viewPager2?.clipChildren = false
            viewPager2?.offscreenPageLimit = 1
            viewPager2?.getChildAt(0)?.overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            infintLoop()
            viewPager2?.isUserInputEnabled = false
        }else{
            relativeLayout_con_viewPager2?.visibility=View.GONE
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


    private fun handleQuestionFragment() {
        fragmentQuestion = FragmentQuestion.newInstance("", "")
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_question_fragment, fragmentQuestion)
            .commit()
    }

    private fun cast() {
        questionCont = findViewById<RelativeLayout>(R.id.question_cont)
        viewPager2=findViewById<ViewPager2>(R.id.viewpager)
        relativeLayout_con_viewPager2=findViewById<RelativeLayout>(R.id.view2)
    }


    private fun makeQuestionFragmentVISIBLE() {
        questionCont?.visibility = View.VISIBLE
    }

    private fun makeQuestionFragmentGONE() {
        questionCont?.setVisibility(View.GONE)
    }

    public fun goBack() {
        finish()
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    override fun onClickedAdsDetails(adsDetails: Ads?) {
        if (adsDetails!!.open_type.equals("1")) {
            showDialogWebView(adsDetails.redirect_url)//keep error
        }
    }

    public fun showDialogWebView(url:String) {
        var shouldRefresh=false
        val dialog= Dialog(this,android.R.style.ThemeOverlay)
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

//    private fun getPlayerIndex(playersList: ArrayList<PlayerBio>, questionsList: ArrayList<String>): Int {
//        val index = 0
//        for (i in 0..playersList.size) {
//            if (!questionsList.contains(playersList[i].photoUrl)) {
//                return i
//            }
//        }
//        return index
//    }
//
//    private fun generateAnswersList(
//        correctAnswer: String,
//        suggestionsList: ArrayList<String>
//    ): ArrayList<String> {
//        val list = ArrayList<String>()
//        for (i in 0..2) {
//            val randomName = generatePlayerAnswers(correctAnswer, suggestionsList, list)
//            list.add(randomName)
//        }
//        return list
//    }
//
//    private fun generatePlayerAnswers(
//        name: String,
//        suggestionList: ArrayList<String>,
//        answersList: ArrayList<String>
//    ): String {
//
//
//        val randomIndex = Random.nextInt(suggestionList.size)
//        val randomElement = suggestionList[randomIndex]
//        println(randomIndex)
//        return if (randomElement != name) {
//            if (!answersList.contains(randomElement))
//                randomElement
//            else {
//                generatePlayerAnswers(name, suggestionList, answersList)
//            }
//        } else {
//            generateAnswers(name, suggestionList, answersList)
//        }
//
//    }
//
//    private fun shuffle(list: MutableList<String>) {
//        // start from the end of the list
//        for (i in list.size - 1 downTo 1) {
//            // get a random index `j` such that `0 <= j <= i`
//            val j = Random.nextInt(i + 1)
//
//            // swap element at i'th position in the list with the element at j'th position
//            val temp = list[i]
//            list[i] = list[j]
//            list[j] = temp
//        }
//    }
}