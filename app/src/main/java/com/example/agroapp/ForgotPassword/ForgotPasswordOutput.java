package com.example.agroapp.ForgotPassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPasswordOutput {
    @SerializedName("responseStatus")
    @Expose
    int responsestatus;
    @SerializedName("responseMessage")
    @Expose
    String responceMessage;

    public int getResponsestatus() {
        return responsestatus;
    }

    public void setResponsestatus(int responsestatus) {
        this.responsestatus = responsestatus;
    }

    public String getResponceMessage() {
        return responceMessage;
    }

    public void setResponceMessage(String responceMessage) {
        this.responceMessage = responceMessage;
    }
}
