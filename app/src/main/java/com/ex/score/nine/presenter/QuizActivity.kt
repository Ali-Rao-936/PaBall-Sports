package com.ex.score.nine.presenter

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.presenter.fragments.quiz_fragments.FragmentQuestion
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("QuizActivity")
@AndroidEntryPoint
class QuizActivity : BaseActivity() {

    var questionCont: RelativeLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        statusBarColor()
        cast()
        handleQuestionFragment()
    }

    private fun handleQuestionFragment() {
        val fragmentQuestion= FragmentQuestion.newInstance("","")
        supportFragmentManager.beginTransaction()
            .replace(R.id.container_question_fragment, fragmentQuestion)
            .commit()
    }
    private fun cast() {
        questionCont=findViewById<RelativeLayout>(R.id.question_cont)
    }


    private  fun makeQuestionFragmentVISIBLE() {
        questionCont?.visibility= View.VISIBLE
    }

    private  fun makeQuestionFragmentGONE() {
        questionCont?.setVisibility(View.GONE)
    }

    public fun goBack() {
        finish()
    }

    private fun statusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

}