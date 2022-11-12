package com.ex.score.nine.utils


import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.TextView
import com.ex.score.nine.R
import com.ex.score.nine.data.ListResponse.prompt_message
import com.ex.score.nine.data.ListResponse.prompt_title



object GeneralTools {

    fun messageDialog(activity:Activity){

        val dialog=Dialog(activity,android.R.style.ThemeOverlay)
        dialog.setContentView(R.layout.popup_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        dialog.findViewById<TextView>(R.id.title_message).setText(prompt_title)
        dialog.findViewById<TextView>(R.id.message).setText(prompt_message)

        dialog.findViewById<View>(R.id.ok_bt).setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}