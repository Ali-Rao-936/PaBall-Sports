package com.ex.score.nine.presentation.fragments.reward_fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.ex.score.nine.R
import com.ex.score.nine.presentation.quiz.QuizActivity
import com.ex.score.nine.presentation.sharedPreferences.QuizInfo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RewardFragment : Fragment() {

    lateinit var txtAmount : TextView
    lateinit var txtDetails : TextView
    lateinit var btnViewLeaderboard : AppCompatButton
    lateinit var btnGoBack : AppCompatButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_reward, container, false)
        inits(view)

        var i = QuizInfo.getCorrectAnswerFromSP(activity?.applicationContext).toInt()
        i *= 10

        txtAmount.text = " $i"
        txtDetails.text = getString(R.string.you_got) +" $i "+getString(R.string.you_are_on)

        // leaderboard
        btnViewLeaderboard.setOnClickListener{
            (activity as QuizActivity?)?.goBack()
        }

        // go back
        btnGoBack.setOnClickListener{
            (activity as QuizActivity?)?.goBack()
        }

        return view
    }

    private fun inits(view: View) {
        txtAmount = view.findViewById(R.id.txtAmount)
        txtDetails = view.findViewById(R.id.reward_message)
        btnViewLeaderboard = view.findViewById(R.id.btnLeaderboard)
        btnGoBack = view.findViewById(R.id.btnGoBack)
    }
}