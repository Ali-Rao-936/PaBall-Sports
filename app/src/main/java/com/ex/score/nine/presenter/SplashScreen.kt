package com.ex.score.nine.presenter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.ex.score.nine.BaseActivity
import com.ex.score.nine.R
import com.ex.score.nine.sharedPreferences.SharedPreference
import com.ex.score.nine.utils.GeneralTools

import java.util.*

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     /*   val locale = Locale(
            SharedPreference.getInstance().getStringValueFromPreference(
                SharedPreference.LOCALE_KEY,
                SharedPreference.ENGLISH,this))

        val configuration = Configuration()
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)*/
        val localLocale=Locale.getDefault().language
        if (localLocale.contains("zh")){
            GeneralTools.setLocale(applicationContext, SharedPreference.CHINESE)
        }
        val languageToLoad = SharedPreference.getInstance().getStringValueFromPreference(
            SharedPreference.LOCALE_KEY,
            SharedPreference.ENGLISH,this) // your language
        val config = applicationContext.resources.configuration
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        config.setLocale(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
           applicationContext.createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)

        setContentView(R.layout.activity_splash)

//        val exo_tv=findViewById<TextView>(R.id.exo_score)
        Handler().postDelayed({
            //sentReqBanner(applicationContext)
        }, 100)

        object : CountDownTimer(2000,2000){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {

                if (SharedPreference.getInstance().getBooleanValueFromPreference(SharedPreference.IS_FIRST_TIME,true,this@SplashScreen))
                startActivity(Intent(this@SplashScreen,OnboardingActivity::class.java))
                else{
                    startActivity(Intent(this@SplashScreen, BaseActivity::class.java))
                }
                finish()
            }

        }.start()
    }

    private fun setLocale(locale: Locale) {
        val resources: Resources = resources
        val configuration: Configuration = resources.configuration
        val displayMetrics: DisplayMetrics = resources.displayMetrics
        configuration.setLocale(locale)
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N) {
            applicationContext.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration, displayMetrics)
        }
    }
}