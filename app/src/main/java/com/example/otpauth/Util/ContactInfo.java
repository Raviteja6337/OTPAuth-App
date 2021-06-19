package com.example.otpauth.Util;

import java.io.Serializable;

public class ContactInfo implements Serializable {
    private String phoneNo;
    private String contactName;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

}
