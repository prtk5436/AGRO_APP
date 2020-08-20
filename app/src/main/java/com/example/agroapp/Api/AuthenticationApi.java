package com.example.agroapp.Api;

import com.example.agroapp.Login.LoginInput;
import com.example.agroapp.Login.LoginOutput;
import com.example.agroapp.Registration.RegistartionInput;
import com.example.agroapp.Registration.RegistrationOutput;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenticationApi {

    @POST("agro/api/api.php?x=user_login")
    Call<LoginOutput> getlogin(@Body LoginInput i);

    @POST("agro/api/api.php?x=user_registration")
    Call<RegistrationOutput> getRegistred(@Body RegistartionInput i);

}
