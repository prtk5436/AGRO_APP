package com.example.agroapp.ErrorLog;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ErrorLogInput {

    @SerializedName("app_name")
    @Expose
    private String appName;
    @SerializedName("error_type")
    @Expose
    private String errorType;
    @SerializedName("error_description")
    @Expose
    private String errorDescription;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("datetime")
    @Expose
    private String datetime;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("member_id")
    @Expose
    private String memberId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("last_prog_name")
    @Expose
    private String lastProgName;
    @SerializedName("webservice_name")
    @Expose
    private String webserviceName;
    @SerializedName("webmethod_name")
    @Expose
    private String webmethodName;
    @SerializedName("prog_err_msg")
    @Expose
    private String progErrMsg;
    @SerializedName("school_id")
    @Expose
    private String schoolId;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastProgName() {
        return lastProgName;
    }

    public void setLastProgName(String lastProgName) {
        this.lastProgName = lastProgName;
    }

    public String getWebserviceName() {
        return webserviceName;
    }

    public void setWebserviceName(String webserviceName) {
        this.webserviceName = webserviceName;
    }

    public String getWebmethodName() {
        return webmethodName;
    }

    public void setWebmethodName(String webmethodName) {
        this.webmethodName = webmethodName;
    }

    public String getProgErrMsg() {
        return progErrMsg;
    }

    public void setProgErrMsg(String progErrMsg) {
        this.progErrMsg = progErrMsg;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

}
