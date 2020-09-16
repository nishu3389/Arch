package com.three.u.util;

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

import static com.three.u.util.Constant.STRIPE_PUBLISHABLE_KEY;

public class UtilJava {
    void jm(BottomNavigationView navigationView){
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                return false;
            }
        });
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

}
