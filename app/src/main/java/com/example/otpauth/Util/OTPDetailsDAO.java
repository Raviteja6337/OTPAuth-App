package com.example.otpauth.Util;

import java.io.Serializable;

public class OTPDetailsDAO implements Serializable {
    private String otpSent;
    private String otpSentTime;
    private String otpSentMobNo;

    public String getOtpSent() {
        return otpSent;
    }

    public void setOtpSent(String otpSent) {
        this.otpSent = otpSent;
    }

    public String getOtpSentTime() {
        return otpSentTime;
    }

    public void setOtpSentTime(String otpSentTime) {
        this.otpSentTime = otpSentTime;
    }

    public String getOtpSentMobNo() {
        return otpSentMobNo;
    }

    public void setOtpSentMobNo(String otpSentMobNo) {
        this.otpSentMobNo = otpSentMobNo;
    }
}
