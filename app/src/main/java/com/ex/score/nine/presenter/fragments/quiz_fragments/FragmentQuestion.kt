package com.ex.score.nine.presenter.fragments.quiz_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ex.score.nine.R
import com.ex.score.nine.presenter.QuizActivity
import com.ex.score.nine.sharedPreferences.QuizInfo.getCorrectAnswerFromSP
import com.ex.score.nine.sharedPreferences.TeamsOrPlayers


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FragmentQuestion : Fragment() {
    // TODO: Rename and change types of parameters
    private var postID: String? = null
    private var param2: String? = null

    var first_details_text: TextView?=null
    var back_rl: RelativeLayout?=null
    var question_number: TextView?=null
    var qp_number_tv: TextView?=null
    var first_details_info: TextView?=null
    var sec_details_text: TextView?=null
    var sec_details_info: TextView?=null
    var thr_details_text: TextView?=null
    var thr_details_info: TextView?=null
    var fou_details_text: TextView?=null
    var fou_details_info: TextView?=null
    var question_number_tv: TextView?=null
    var image_view: ImageView?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            postID = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_question, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cast(view)
        checkIfTeamsOrPlayers()
        actionListenerToBack()
        setNumberOfPointsCollect()
        fillImage()
    }

    private fun fillImage() {
        //activity?.applicationContext?.let { Glide.with(it).load("adsf").into(image_view!!) }
    }

    private fun setNumberOfPointsCollect() {
        var i = getCorrectAnswerFromSP(activity?.applicationContext).toInt()
        i=i*10

        question_number?.setText(activity?.resources?.getString(R.string.question_number)+" "+getCorrectAnswerFromSP(activity?.applicationContext))
        qp_number_tv?.setText(activity?.resources?.getString(R.string.qp)+" "+ java.lang.String.valueOf(i))

    }

    private fun actionListenerToBack() {
        back_rl?.setOnClickListener{
            (activity as QuizActivity?)?.goBack()
        }
    }

    private fun checkIfTeamsOrPlayers() {
        if (!TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).isEmpty()
            &&
            (TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).equals("empty")
                    || TeamsOrPlayers.getTeamsOrPlayersInSP(activity?.applicationContext).equals("teams")))
        {
//            teams_radio.visibility= View.VISIBLE
            first_details_text?.setText(activity?.resources?.getString(R.string.goal))
            first_details_info?.setText("point")

            sec_details_text?.setText(activity?.resources?.getString(R.string.top_score))
            sec_details_info?.setText("top_score")

            thr_details_text?.setText(activity?.resources?.getString(R.string.league))
            thr_details_info?.setText("league name")

            fou_details_text?.setText(activity?.resources?.getString(R.string.team))
            fou_details_info?.setText("team name")
        }else{
            first_details_text?.setText(activity?.resources?.getString(R.string.league))
            first_details_info?.setText("league_name")

            thr_details_text?.setText(activity?.resources?.getString(R.string.current_location))
            thr_details_info?.setText("current_location")


            sec_details_text?.visibility= View.GONE
            sec_details_info?.visibility= View.GONE

            fou_details_text?.visibility= View.GONE
            fou_details_info?.visibility= View.GONE
        }
    }

    private fun cast(view: View) {
        back_rl=view.findViewById<RelativeLayout>(R.id.back_rl)
        question_number=view.findViewById<TextView>(R.id.question_number_tv)
        qp_number_tv=view.findViewById<TextView>(R.id.qp_number_tv)

        first_details_text=view.findViewById<TextView>(R.id.first_details_text)
        first_details_info=view.findViewById<TextView>(R.id.first_details_info)
        sec_details_text=view.findViewById<TextView>(R.id.sec_details_text)
        sec_details_info=view.findViewById<TextView>(R.id.sec_details_info)
        thr_details_text=view.findViewById<TextView>(R.id.thr_details_text)
        thr_details_info=view.findViewById<TextView>(R.id.thr_details_info)
        fou_details_text=view.findViewById<TextView>(R.id.fou_details_text)
        fou_details_info=view.findViewById<TextView>(R.id.fou_details_info)
        question_number_tv=view.findViewById<TextView>(R.id.question_number_tv)
        image_view=view.findViewById<ImageView>(R.id.image_view)

    }

    companion object {
        @JvmStatic fun newInstance(postID: String, unused: String) =
            FragmentQuestion().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, postID)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }


}