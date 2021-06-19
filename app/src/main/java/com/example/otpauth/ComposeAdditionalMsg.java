package com.example.otpauth;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.otpauth.Util.OTPDetailsDAO;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ComposeAdditionalMsg extends AppCompatActivity {

    private static final String TAG = "ComposeAdditionalMsg";

    TextView otpTxtView;
    Context context;
    EditText editTextMsg;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        context = this;
        setContentView(R.layout.extra_msg_screen3);

        //Setting generated 6 digit OTP
        otpTxtView = (TextView) findViewById(R.id.otpSndtTxtVw);
        otpTxtView.setText(getIntent().getStringExtra("otpToSend"));

        String mobNumToSend = getIntent().getStringExtra("mobnoToSend");

        editTextMsg = (EditText) findViewById(R.id.editTextMsg);
        editTextMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        //Proceed Btn onClick
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    submitBtn.setClickable(false);
                    /* Text Local SMS API not working */
//                    SendSMS sendSMS = new SendSMS(getApplicationContext(),(String) otpTxtView.getText());
//                    sendSMS.execute();

                    //Fast2SMS API working
                    OkHttpClient client = new OkHttpClient();

                    RequestBody formBody = new FormBody.Builder()
                            .add("message",(String)otpTxtView.getText() + "\nPlease use this as your one time password Zoop.com\n"+editTextMsg.getText())
                            .add("language","english")
                            .add("route","q")
                            .add("numbers", mobNumToSend)
                            .build();
                    Request request = new Request.Builder().addHeader("Content-Type","application/json").addHeader("authorization","Aq5PsCX0jPmRoqMCHctx0i927MsipYReSPz8nrzatykZwPQkZKAB74JW3TBp")
                            .url("https://www.fast2sms.com/dev/bulkV2") // The URL to send the data to
                            .post(formBody)
                            .build();

                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {
                            call.cancel();

                            Toast.makeText(context,"Message Sending Failed", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            assert response.body() != null;
                            Log.d(TAG,response.body().string());

                            try {
                                DataBaseHandler dataBaseHandler = new DataBaseHandler(context);

                                OTPDetailsDAO otpDetailsDAO = new OTPDetailsDAO();
                                otpDetailsDAO.setOtpSent((String) otpTxtView.getText());
                                otpDetailsDAO.setOtpSentMobNo(mobNumToSend);

                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd _ HH:mm", Locale.getDefault());
                                String currentDateandTime = sdf.format(new Date());
                                otpDetailsDAO.setOtpSentTime(currentDateandTime);

                                dataBaseHandler.addOTPDetails(otpDetailsDAO);
                            }
                            catch (Exception e)
                            {
                                Log.d(TAG,"Error in adding OTP Details to DB");
                            }

                            //Calling the finish when submit button is clicked (can add this only to success response to modify further)
                            finish();
                        }
                    });

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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
