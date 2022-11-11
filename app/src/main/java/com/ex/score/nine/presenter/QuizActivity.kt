package com.ex.score.nine.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.utils.Constants
import com.ex.score.nine.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class QuizActivity : BaseActivity() {

    var loginStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quiz_activity)

        goToNext()
    }

    private  fun goToNext() {
        lifecycleScope.launch {
            loginStatus = Utils.getBooleanValue(preferences, Constants.IS_FIRST_TIME).first() ?: true

           val intent = if (loginStatus) {
                 Intent(this@QuizActivity, OnboardingActivity::class.java)
            }else{
               Intent(this@QuizActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()


        }
    }
}