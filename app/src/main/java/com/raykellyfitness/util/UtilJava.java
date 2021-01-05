package com.raykellyfitness.util;

import android.content.Context;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.Stripe;
import com.stripe.android.model.BankAccountTokenParams;
import com.stripe.android.model.Token;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import static com.raykellyfitness.util.Constant.STRIPE_PUBLISHABLE_KEY;

public class UtilJava {
    void jm(BottomNavigationView navigationView){
        navigationView.setOnNavigationItemSelectedListener(item -> false);
    }

    // ("US","USD","000123456789", BankAccountTokenParams.Type.Individual,"Avinash","110000000");
    public static MutableLiveData<String> liveData;
    public static MutableLiveData<String> getBankAccountToken(Context context, String country, String currency, String accountNumber, BankAccountTokenParams.Type type, String accountHolderName, String routingNumber ){
        liveData = new MutableLiveData<>();
        Stripe stripe = new Stripe(context, STRIPE_PUBLISHABLE_KEY);

        BankAccountTokenParams tokenParams = new BankAccountTokenParams(country,currency,accountNumber, type,accountHolderName,routingNumber);

        stripe.createBankAccountToken(tokenParams, new ApiResultCallback<Token>() {
            @Override
            public void onSuccess(Token token) {
                liveData.setValue(token.getId());
            }

            @Override
            public void onError(@NotNull Exception e) {
                liveData.setValue(null);
                Util.Companion.showErrorSneaker(context,e.getMessage());
            }
        });

        return liveData;
    }

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
