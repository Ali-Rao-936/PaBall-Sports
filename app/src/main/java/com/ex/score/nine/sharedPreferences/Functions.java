package com.ex.score.nine.sharedPreferences;


import static com.ex.score.nine.data.ListResponse.prompt_frequency;
import static com.ex.score.nine.sharedPreferences.PromptFrequency.getPromptFrequencyFromSP;
import static com.ex.score.nine.sharedPreferences.PromptFrequency.savePromptFrequencyInSP;

import android.content.Context;
import android.util.Log;


public class Functions {

    public static boolean showPopupMessageCheck(Context context) {
        boolean canOrCant=false;
        String prompt_frequency_sp =getPromptFrequencyFromSP(context);

        int x= Integer.parseInt(prompt_frequency_sp);
        if (prompt_frequency ==null || prompt_frequency.isEmpty())
        {
            canOrCant =false;
        }else{
            int y= Integer.parseInt(prompt_frequency);
            if (x>y)
            {
                canOrCant =false;
            }else{
                canOrCant =true;
                increaseNumberOfHowManyTimeIShowedMessageBox(context);
            }
        }


        return canOrCant;
    }

    public static void increaseNumberOfHowManyTimeIShowedMessageBox(Context context) {
        String prompt_frequency_sp =getPromptFrequencyFromSP(context);
        Log.i("TAG","prompt_frequency_sp: "+prompt_frequency_sp);

        if (prompt_frequency_sp.equals("0"))
        {
            savePromptFrequencyInSP(context,"1");
        }else{
            int x= Integer.parseInt(prompt_frequency_sp);
            x=x+1;
            savePromptFrequencyInSP(context,String.valueOf(x));
            Log.i("TAG","prompt_frequency_sp: "+prompt_frequency_sp);
        }
    }

}
