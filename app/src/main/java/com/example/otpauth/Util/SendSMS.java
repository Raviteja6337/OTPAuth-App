package com.example.otpauth.Util;

import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;




//TODO NOT WORKING 'TEXTLOCAL MSG API'


public class SendSMS extends AsyncTask<String, String, String> {
    private static final String API_KEY = "NGE3MjMyNjE0NjY2NTA2NzQ3Mzc2MTQ0NWE0Zjc3NGU=";
    Context activityCtx;
    String strOTP;

    public SendSMS(Context context, String otp) throws IOException {
        this.activityCtx = context;
        strOTP = otp;
    }

    public String sendSms() {
        try {

            //Construct data
            String apiKey = "apikey=" + "Mzk3NTZlNjQ0Nzc1MzUzMTcxNjQ0MjRjMzc3MTY4NTM=";
            String message = "&message=" + "Hello";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "918341020840";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://www.fast2sms.com/dev/bulkV2").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

            return stringBuffer.toString();

        } catch (Exception e) {
            System.out.println("Error SMS "+e);
            return "Error "+e;
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        return sendSms();
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}
