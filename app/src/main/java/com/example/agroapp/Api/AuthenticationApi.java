package com.example.agroapp.Api;

import com.example.agroapp.AddForm.AddFormDataInput;
import com.example.agroapp.AddForm.AddFormDatatOutput;
import com.example.agroapp.ErrorLog.ErrorLogInput;
import com.example.agroapp.ErrorLog.ErrorLogOutput;
import com.example.agroapp.ForgotPassword.ForgotPasswordInput;
import com.example.agroapp.ForgotPassword.ForgotPasswordOutput;
import com.example.agroapp.Login.LoginInput;
import com.example.agroapp.Login.LoginOutput;
import com.example.agroapp.Registration.RegistartionInput;
import com.example.agroapp.Registration.RegistrationOutput;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {
    //User Login
    @POST("agro/api/api.php?x=user_login")
    Call<LoginOutput> getlogin(@Body LoginInput i);

    //User Registration
    @POST("agro/api/api.php?x=user_registration")
    Call<RegistrationOutput> getRegistred(@Body RegistartionInput i);

    //Add Form Data
    @POST("agro/api/api.php?x=form_data_added")
    Call<AddFormDatatOutput> sendData(@Body AddFormDataInput i);

    //Forgot Password
    @POST("agro/api/api.php?x=forgot_password")
    Call<ForgotPasswordOutput> sendPassword(@Body ForgotPasswordInput i);


    //Forgot Password
    @POST("core/error_log_ws.php")
    Call<ErrorLogOutput> getErrorLog(@Body ErrorLogInput i);
}
