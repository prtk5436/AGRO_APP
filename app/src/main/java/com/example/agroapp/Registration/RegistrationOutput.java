package com.example.agroapp.Registration;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegistrationOutput {
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
