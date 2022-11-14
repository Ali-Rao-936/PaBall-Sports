package com.pa.ball.sports.quiz.presentation.sharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class QuizInfo {

    private static final String QUIZ_INFO = "QUIZ_INFO";
    static SharedPreferences SharedPreferences;
    static SharedPreferences.Editor Editor;


    public static void saveNumberOfCorrectAnswerInSP(Context context, String text) {
        SharedPreferences = context.getSharedPreferences(QUIZ_INFO, MODE_PRIVATE);
        Editor = SharedPreferences.edit();

        Editor.putString("correct_answer",text);

        Editor.commit();

    }



    public static String getCorrectAnswerFromSP(Context context) {
        String text;
        SharedPreferences shared = context.getSharedPreferences(QUIZ_INFO, MODE_PRIVATE);

        text = (shared.getString("correct_answer", ""));
        if (text == null || text.isEmpty())
        {
            text = "1";
        }
        return text;
    }

    public static void increCorrectAnswerFromSP(Context context) {
        String text=getCorrectAnswerFromSP(context);
        int i = Integer.parseInt(text);
        i=i+1;
        saveNumberOfCorrectAnswerInSP(context,String.valueOf(i));

    }

    public static void resetACountCorrectAnswerFromSP(Context context) {
        saveNumberOfCorrectAnswerInSP(context,"1");

    }


}
