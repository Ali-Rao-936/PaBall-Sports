package com.ex.score.nine.presentation.sharedPreferences;


import static com.ex.score.nine.data.ListResponse.prompt_frequency;
import static com.ex.score.nine.presentation.sharedPreferences.PromptFrequency.getPromptFrequencyFromSP;
import static com.ex.score.nine.presentation.sharedPreferences.PromptFrequency.savePromptFrequencyInSP;

import android.content.Context;
import android.util.Log;

import com.ex.score.nine.R;
import com.ex.score.nine.domain.models.AnswersModelT;
import com.ex.score.nine.domain.models.Scores;

import java.util.ArrayList;


public class Functions {

    public static boolean showPopupMessageCheck(Context context) {
        boolean canOrCant = false;
        String prompt_frequency_sp = getPromptFrequencyFromSP(context);

        int x = Integer.parseInt(prompt_frequency_sp);
        if (prompt_frequency == null || prompt_frequency.isEmpty()) {
            canOrCant = false;
        } else {
            int y = Integer.parseInt(prompt_frequency);
            if (x > y) {
                canOrCant = false;
            } else {
                canOrCant = true;
                increaseNumberOfHowManyTimeIShowedMessageBox(context);
            }
        }


        return canOrCant;
    }

    public static void increaseNumberOfHowManyTimeIShowedMessageBox(Context context) {
        String prompt_frequency_sp = getPromptFrequencyFromSP(context);
        Log.i("TAG", "prompt_frequency_sp: " + prompt_frequency_sp);

        if (prompt_frequency_sp.equals("0")) {
            savePromptFrequencyInSP(context, "1");
        } else {
            int x = Integer.parseInt(prompt_frequency_sp);
            x = x + 1;
            savePromptFrequencyInSP(context, String.valueOf(x));
            Log.i("TAG", "prompt_frequency_sp: " + prompt_frequency_sp);
        }
    }

    public static ArrayList<Scores> fillSoccer() {
        ArrayList<Scores> scoresArrayList = new ArrayList<>();
        scoresArrayList.add(new Scores("James", "5030"));
        scoresArrayList.add(new Scores("Robert", "4990"));
        scoresArrayList.add(new Scores("John", "4980"));
        scoresArrayList.add(new Scores("Michael", "4900"));
        scoresArrayList.add(new Scores("David", "4880"));

        scoresArrayList.add(new Scores("William", "4840"));
        scoresArrayList.add(new Scores("Richard", "4830"));
        scoresArrayList.add(new Scores("Joseph", "4810"));
        scoresArrayList.add(new Scores("Thomas", "4800"));
        scoresArrayList.add(new Scores("Charles", "4770"));

        scoresArrayList.add(new Scores("Christopher", "4730"));
        scoresArrayList.add(new Scores("Daniel", "4650"));
        scoresArrayList.add(new Scores("Matthew", "4630"));
        scoresArrayList.add(new Scores("Anthony", "4620"));
        scoresArrayList.add(new Scores("Mark", "4590"));

        scoresArrayList.add(new Scores("Donald", "4570"));
        scoresArrayList.add(new Scores("Steven", "4530"));
        scoresArrayList.add(new Scores("Paul", "4510"));
        scoresArrayList.add(new Scores("Andrew", "4500"));
        scoresArrayList.add(new Scores("Joshua", "4490"));

        scoresArrayList.add(new Scores("Kenneth", "4470"));
        scoresArrayList.add(new Scores("Kevin", "4460"));
        scoresArrayList.add(new Scores("Brian", "4450"));
        scoresArrayList.add(new Scores("George", "4440"));
        scoresArrayList.add(new Scores("Timothy", "4420"));

        scoresArrayList.add(new Scores("Ronald", "4340"));
        scoresArrayList.add(new Scores("Edward", "4290"));
        scoresArrayList.add(new Scores("Jason", "4210"));
        scoresArrayList.add(new Scores("Jeffrey", "4190"));
        scoresArrayList.add(new Scores("Ryan", "4170"));

        scoresArrayList.add(new Scores("Jacob", "4090"));
        scoresArrayList.add(new Scores("Gary", "4020"));
        scoresArrayList.add(new Scores("Nicholas", "3990"));
        scoresArrayList.add(new Scores("Eric", "3920"));
        scoresArrayList.add(new Scores("Jonathan", "3820"));

        scoresArrayList.add(new Scores("Benjamin", "3760"));
        scoresArrayList.add(new Scores("Samuel", "3710"));
        scoresArrayList.add(new Scores("Gregory", "3620"));
        scoresArrayList.add(new Scores("Alexander", "3590"));
        scoresArrayList.add(new Scores("Frank", "3520"));

        scoresArrayList.add(new Scores("Patrick", "3410"));
        scoresArrayList.add(new Scores("Raymond", "3360"));
        scoresArrayList.add(new Scores("Jack", "3270"));
        scoresArrayList.add(new Scores("Dennis", "3110"));
        scoresArrayList.add(new Scores("Jerry", "3020"));

        scoresArrayList.add(new Scores("Tyler", "2990"));
        scoresArrayList.add(new Scores("Aaron", "2960"));
        scoresArrayList.add(new Scores("Jose", "2910"));
        scoresArrayList.add(new Scores("Adam", "2900"));
        scoresArrayList.add(new Scores("Nathan", "2860"));

        scoresArrayList.add(new Scores("Henry", "2840"));
        scoresArrayList.add(new Scores("Douglas", "2810"));
        scoresArrayList.add(new Scores("Zachary", "2750"));
        scoresArrayList.add(new Scores("Peter", "2710"));
        scoresArrayList.add(new Scores("Kyle", "2660"));

        scoresArrayList.add(new Scores("Ethan", "2610"));
        scoresArrayList.add(new Scores("Walter", "2580"));
        scoresArrayList.add(new Scores("Noah", "2550"));
        scoresArrayList.add(new Scores("Jeremy", "2530"));
        scoresArrayList.add(new Scores("Christian", "2500"));

        scoresArrayList.add(new Scores("Keith", "2470"));
        scoresArrayList.add(new Scores("Roger", "2460"));
        scoresArrayList.add(new Scores("Terry", "2400"));
        scoresArrayList.add(new Scores("Gerald", "2390"));
        scoresArrayList.add(new Scores("Harold", "2330"));

        scoresArrayList.add(new Scores("Sean", "2300"));
        scoresArrayList.add(new Scores("Austin", "2240"));
        scoresArrayList.add(new Scores("Carl", "2210"));
        scoresArrayList.add(new Scores("Arthur", "2160"));
        scoresArrayList.add(new Scores("Lawrence", "2130"));

        scoresArrayList.add(new Scores("Dylan", "2080"));
        scoresArrayList.add(new Scores("Jesse", "2030"));
        scoresArrayList.add(new Scores("Jordan", "2010"));
        scoresArrayList.add(new Scores("Bryan", "2000"));
        scoresArrayList.add(new Scores("Billy", "1990"));

        scoresArrayList.add(new Scores("Joe", "1970"));
        scoresArrayList.add(new Scores("Bruce", "1940"));
        scoresArrayList.add(new Scores("Gabriel", "1890"));
        scoresArrayList.add(new Scores("Logan", "1830"));
        scoresArrayList.add(new Scores("Albert", "1820"));

        scoresArrayList.add(new Scores("Willie", "1800"));
        scoresArrayList.add(new Scores("Alan", "1760"));
        scoresArrayList.add(new Scores("Juan", "1740"));
        scoresArrayList.add(new Scores("Wayne", "1720"));
        scoresArrayList.add(new Scores("Elijah", "1670"));

        scoresArrayList.add(new Scores("Randy", "1630"));
        scoresArrayList.add(new Scores("Roy", "1620"));
        scoresArrayList.add(new Scores("Vincent", "1590"));
        scoresArrayList.add(new Scores("Ralph", "1530"));
        scoresArrayList.add(new Scores("Eugene", "1520"));

        scoresArrayList.add(new Scores("Russell", "1500"));
        scoresArrayList.add(new Scores("Bobby", "1480"));
        scoresArrayList.add(new Scores("Mason", "1420"));
        scoresArrayList.add(new Scores("Philip", "1410"));
        scoresArrayList.add(new Scores("Louis", "1350"));

        scoresArrayList.add(new Scores("Omar", "1310"));
        scoresArrayList.add(new Scores("Mohammad", "1230"));
        scoresArrayList.add(new Scores("Khaled", "1090"));
        scoresArrayList.add(new Scores("Obeidat", "1030"));
        scoresArrayList.add(new Scores("Anwar", "850"));

        return scoresArrayList;
    }


    public static ArrayList<AnswersModelT> fillTestAnswer(Context context) {

        ArrayList<AnswersModelT> scoresArrayList = new ArrayList<>();
        scoresArrayList.add(new AnswersModelT(false, "Jordan",context.getResources().getString(R.string.a)));
        scoresArrayList.add(new AnswersModelT(true, "Egpt",context.getResources().getString(R.string.b)));
        scoresArrayList.add(new AnswersModelT(false, "UAE",context.getResources().getString(R.string.c)));
        scoresArrayList.add(new AnswersModelT(false, "USA",context.getResources().getString(R.string.d)));


        return scoresArrayList;
    }

}
