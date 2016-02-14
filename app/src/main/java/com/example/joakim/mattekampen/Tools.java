package com.example.joakim.mattekampen;

import android.text.SpannableString;
import android.text.style.UnderlineSpan;

/**
 * Created by Joakim on 2015-06-07.
 */
public class Tools {

    public static SpannableString getUnderlinedText(String text){

        SpannableString theText = new SpannableString(text);
        theText.setSpan(new UnderlineSpan(), 0, theText.length(), 0);

        return theText;
    }

    public static int calculateTextSize(String questionLine) {
        if (questionLine.length() < 26)
            return 25;
        else if (questionLine.length() < 37)
            return 20;
        else
            return 16;
    }
}
