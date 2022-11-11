package com.ex.score.nine.presenter.fragments.quiz_fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.ex.score.nine.R;
import com.ex.score.nine.presenter.QuizActivity;


public class FragmentQuestion extends Fragment {
    View view;
    public FragmentQuestion(){}
    String url;

    @Override
    public void onAttach(Context context) {
        if (getArguments() != null) {
//            url = getArguments().getString("url");
        }
        super.onAttach(context);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.fragment_question, container, false);

        inti();

        return view;
    }





    private void inti() {
//        webView = view.findViewById(R.id.web_vew);
//        backImage = view.findViewById(R.id.back_button);
    }

}
