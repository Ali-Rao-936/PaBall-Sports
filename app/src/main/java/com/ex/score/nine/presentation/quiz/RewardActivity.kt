package com.ex.score.nine.presentation.quiz

import android.os.Bundle
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RewardActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reward)

    }
}