package com.ex.score.nine.presenter.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.ex.score.nine.R
import com.ex.score.nine.presentation.fragments.onboarding.OnboardingFragment

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class OnboardingFragment : Fragment() {
    private var resID: Int? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            resID = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_onboarding, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Glide.with(requireContext())
            .load(resID!!)
            .into(view.findViewById(R.id.onboarding_image))
    }

    companion object {
        @JvmStatic
        fun newInstance(resId: Int, param2: String) =
            OnboardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, resId)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}