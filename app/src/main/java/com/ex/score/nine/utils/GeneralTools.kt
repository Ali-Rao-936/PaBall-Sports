package com.ex.score.nine.utils


import android.content.Context

import com.ex.score.nine.sharedPreferences.SharedPreference



object GeneralTools {


    fun setLocale(context: Context,locale:String) {
        SharedPreference.getInstance().saveStringToPreferences(SharedPreference.LOCALE_KEY,locale,context)
    }


}