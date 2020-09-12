package com.example.agroapp.Util;

import android.util.Log;

import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.ErrorLog.ErrorLogInput;
import com.example.agroapp.ErrorLog.ErrorLogOutput;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommonFunctions {

    private static final String TAG = "CommonFunctions";

    public static void errorLog(String appname, String error_type, String error_desc, String data, String UserType, String MemberId,
                                String name, String Phone, String email, String status, String LastProgName, String webServiceName, String WebmethodName, String progErrMsg, String schoolID) {

        Date currentTime = Calendar.getInstance().getTime();
        AuthenticationApi authenticationApi = ApiClient.getClient1().create(AuthenticationApi.class);
        ErrorLogInput i = new ErrorLogInput();
        i.setAppName(appname);
        i.setErrorType(error_type);
        i.setErrorDescription(error_desc);
        i.setData(data);
        i.setDatetime(String.valueOf(currentTime));
        i.setUserType(UserType);
        i.setMemberId(MemberId);
        i.setName(name);
        i.setPhone(Phone);
        i.setEmail(email);
        i.setStatus(status);
        i.setLastProgName(LastProgName);
        i.setWebserviceName(webServiceName);
        i.setWebmethodName(WebmethodName);
        i.setProgErrMsg(progErrMsg);
        i.setSchoolId(schoolID);
        Log.d(TAG, "errorLog() called with: appname = [" + appname + "], error_type = [" + error_type + "], error_desc = [" + error_desc + "], data = [" + data + "], UserType = [" + UserType + "], MemberId = [" + MemberId + "], name = [" + name + "], Phone = [" + Phone + "], email = [" + email + "], status = [" + status + "], LastProgName = [" + LastProgName + "], webServiceName = [" + webServiceName + "], WebmethodName = [" + WebmethodName + "], progErrMsg = [" + progErrMsg + "], schoolID = [" + schoolID + "]");

        Log.d(TAG, "errorLog: i =  " + i);
        Call<ErrorLogOutput> call = authenticationApi.getErrorLog(i);

        Log.d(TAG, "errorLog: call =  " + call);
        call.enqueue(new Callback<ErrorLogOutput>() {
            @Override
            public void onResponse(Call<ErrorLogOutput> call, Response<ErrorLogOutput> response) {

                // Toast.makeText(getApplicationContext(),"Error Log traked", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ErrorLogOutput> call, Throwable t) {

            }
        });

    }
}
