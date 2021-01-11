package com.raykellyfitness.util;

import androidx.lifecycle.MutableLiveData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class UtilJava {

    void jm(BottomNavigationView navigationView){
        navigationView.setOnNavigationItemSelectedListener(item -> false);
    }

    // ("US","USD","000123456789", BankAccountTokenParams.Type.Individual,"Avinash","110000000");
    public static MutableLiveData<String> liveData;

    public static String convertUtcToLocal(String ourDate, String inputFormat, String outputFormat)
    {
        try
        {
            SimpleDateFormat formatter = new SimpleDateFormat(inputFormat);
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date value = formatter.parse(ourDate);

            SimpleDateFormat dateFormatter = new SimpleDateFormat(outputFormat); //this format changeable
            dateFormatter.setTimeZone(TimeZone.getDefault());
            ourDate = dateFormatter.format(value);

            //Log.d("ourDate", ourDate);
        }
        catch (Exception e)
        {
            ourDate = "00-00-0000 00:00";
        }
        return ourDate;
    }

}
