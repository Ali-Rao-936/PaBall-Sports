package com.ex.score.nine.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
class SplashActivity : BaseActivity() {

    var loginStatus: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        goToNext()
    }

    private  fun goToNext() {
        lifecycleScope.launch {
            loginStatus = Utils.getBooleanValue(preferences, Constants.IS_FIRST_TIME).first() ?: true

           val intent = if (loginStatus) {
                 Intent(this@SplashActivity, OnboardingActivity::class.java)
            }else{
               Intent(this@SplashActivity, MainActivity::class.java)
            }
            startActivity(intent)
            finish()


        }
    }
}