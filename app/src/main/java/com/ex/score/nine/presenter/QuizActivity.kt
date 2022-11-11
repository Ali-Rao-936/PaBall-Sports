package com.ex.score.nine.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.presenter.fragments.quiz_fragments.FragmentQuestion
import com.ex.score.nine.utils.Constants
import com.ex.score.nine.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("QuizActivity")
@AndroidEntryPoint
class QuizActivity : BaseActivity() {

    var loginStatus: Boolean = false
    var fragmentQuestion: FragmentQuestion = FragmentQuestion()
    var questionCont: RelativeLayout?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        cast()

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

}