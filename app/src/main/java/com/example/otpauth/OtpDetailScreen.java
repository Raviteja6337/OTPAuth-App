package com.example.otpauth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.otpauth.Util.ContactInfo;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class OtpDetailScreen extends AppCompatActivity {

    ContactInfo cntInfo;
    CountDownTimer cTimer = null;
    TextView timerTxtView;
    TextView otpTxtView;
    Context context;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.otp_details_screen2);
        cntInfo = (ContactInfo) getIntent().getSerializableExtra("ContactInfoObj");

        TextView cnctNmTxtView = (TextView) findViewById(R.id.otpdetail_cntctName);
        cnctNmTxtView.setText(cntInfo.getContactName());

        TextView mobNoTxtView = (TextView) findViewById(R.id.otpdetail_mobno);
        mobNoTxtView.setText(cntInfo.getPhoneNo());

        timerTxtView = (TextView) findViewById(R.id.otp_timer);

        //Generate OTP & Setting it to the otpTxtView
        otpTxtView = (TextView) findViewById(R.id.otpTxtVw);
        otpTxtView.setText(String.valueOf(generateOTP(6)));

        //start the CountDown Timer
        startTimer();

        //Proceed Btn onClick
        Button proceedBtn = (Button) findViewById(R.id.proceedBtn);
        proceedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent otpDetailIntnt = new Intent(context, ComposeAdditionalMsg.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("otpToSend",(String)otpTxtView.getText());
                    bundle.putString("mobnoToSend",(String) mobNoTxtView.getText());

                    otpDetailIntnt.putExtras(bundle);

                    context.startActivity(otpDetailIntnt);

                    finish();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cntInfo = null;
        cancelTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cancelTimer();
    }


    private static char[] generateOTP(int length) {
        String numbers = "1234567890";
        Random random = new Random();
        char[] otp = new char[length];

        for(int i = 0; i< length ; i++) {
            otp[i] = numbers.charAt(random.nextInt(numbers.length()));
        }
        return otp;
    }

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(150000, 1000) {
            public void onTick(long millisUntilFinished) {
//                timerTxtView.setText(getResources().getString(R.string.time_txt).concat(String.valueOf(millisUntilFinished / 1000)));
                @SuppressLint("DefaultLocale")
                String str = getResources().getString(R.string.time_txt)+" "+String.format("%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));
                timerTxtView.setText(str);
            }
            public void onFinish() {
                timerTxtView.setText(getResources().getString(R.string.done_txt));
                Log.i("OTPTimer","Timer Finish");
            }
        };
        cTimer.start();
    }

    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }
}
