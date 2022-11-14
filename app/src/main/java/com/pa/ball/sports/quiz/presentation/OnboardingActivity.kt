package com.pa.ball.sports.quiz.presentation

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.pa.ball.sports.quiz.BaseActivity
import com.pa.ball.sports.quiz.R
import com.pa.ball.sports.quiz.domain.models.OnboardingObject
import com.pa.ball.sports.quiz.presentation.adapters.ViewPagerAdapter
import com.pa.ball.sports.quiz.presentation.fragments.onboarding.OnboardingFragment
import com.pa.ball.sports.quiz.presentation.sharedPreferences.SharedPreference
import com.pa.ball.sports.quiz.utils.Constants
import com.pa.ball.sports.quiz.utils.Utils
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.*

@AndroidEntryPoint
class OnboardingActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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

        setContentView(R.layout.activity_onboarding)
        //statusBarColor()

        val viewpager = findViewById<ViewPager2>(R.id.viewpager_onboarding)
        val wormDots = findViewById<WormDotsIndicator>(R.id.onbaording_dots_indicator)
        val heading = findViewById<TextView>(R.id.onboarding_heading)
        val body = findViewById<TextView>(R.id.onboarding_body)
        val list = ArrayList<OnboardingObject>()

        list.add(
            OnboardingObject(
                R.drawable.onboarding_1,
                getString(R.string.onboarding_header_1),
                getString(R.string.onboarding_body_1)
            )
        )
        list.add(
            OnboardingObject(
                R.drawable.onboarding_2,
                getString(R.string.onboarding_header_2),
                getString(R.string.onboarding_body_2)
            )
        )
        list.add(
            OnboardingObject(
                R.drawable.onboarding_3,
                getString(R.string.onboarding_header_3),
                getString(R.string.onboarding_body_3)
            )
        )

        val frags = ArrayList<Fragment>()
        list.forEach {
            frags.add(OnboardingFragment.newInstance(it.resId, ""))
        }
        viewpager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle, frags)
        viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                heading.text = list[position].heading
                body.text = list[position].body
            }
        })
        wormDots.attachTo(viewpager)
        findViewById<View>(R.id.next_bt).setOnClickListener {
            val pos = viewpager.currentItem + 1
            viewpager.setCurrentItem(pos, true)
            if (pos == viewpager!!.adapter!!.itemCount) {
                // SharedPreference.getInstance().saveBooleanToPreferences(SharedPreference.IS_FIRST_TIME,false,this)
                lifecycleScope.launch {
                    Utils.addBooleanValue(preferences, Constants.IS_FIRST_TIME, false)
                }
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }

    }
}