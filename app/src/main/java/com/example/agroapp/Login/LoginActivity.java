package com.example.agroapp.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agroapp.AddForm.AddFormData;
import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.Api.FeatureController;
import com.example.agroapp.ForgotPassword.ForgotPasswordInput;
import com.example.agroapp.ForgotPassword.ForgotPasswordOutput;
import com.example.agroapp.R;
import com.example.agroapp.Registration.RegistrationActivity;
import com.scwang.wave.MultiWaveHeader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    MultiWaveHeader header, footer;
    EditText edtUsername, edtPassword, et_mob;
    String username, password, str_mob = "";
    Button btnlogin, btn_get_pwd;
    FeatureController featureController;
    TextView txt_signup, tv_forgotPWD;
    ProgressBar prog;
    LinearLayout l1, l2, l3;

    private String isLoggedIn = "false";
    public static final String mobileNumber = "mobileNumber";
    public static final String pwd = "password";
    private SharedPreferences sharedpreferences;
    UserDetail userDetail;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        context = this;
        sharedpreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        if (sharedpreferences.getString(isLoggedIn, "false").equals("true")) {
            username = sharedpreferences.getString(mobileNumber, "");
            password = sharedpreferences.getString(pwd, "");
            Login();
           /* Intent i = new Intent(DrLoginActivity.this,DrDashboardActivity.class);
            startActivity(i);*/
        }
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                password = edtPassword.getText().toString();
                username = edtUsername.getText().toString();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "please enter valid username & password", Toast.LENGTH_LONG).show();
                } else {
                    l1.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.GONE);
                    prog.setVisibility(View.VISIBLE);
                    Login();
                }
            }
        });
        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });
        tv_forgotPWD.setOnClickListener(this);
        btn_get_pwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_forgot:
                l1.setVisibility(View.GONE);
                l3.setVisibility(View.GONE);
                btnlogin.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                btn_get_pwd.setVisibility(View.VISIBLE);

                break;

            case R.id.new_pwd:
                getNewPassword();
                break;
        }
    }

    private void init() {
        header = findViewById(R.id.wave_header);
        footer = findViewById(R.id.wave_footer);
        header.setGradientAngle(45);
        header.setVelocity(1);
        header.setProgress(1);
        header.setStartColor(Color.CYAN);
        header.setCloseColor(Color.CYAN);
        header.setWaveHeight(40);
        header.isRunning();
        footer.setGradientAngle(45);
        footer.setVelocity(1);
        footer.setProgress(1);
        footer.setWaveHeight(40);
        footer.setStartColor(Color.CYAN);
        footer.setCloseColor(Color.CYAN);
        footer.isRunning();
        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnlogin = findViewById(R.id.login);
        txt_signup = findViewById(R.id.txt_signup);
        tv_forgotPWD = findViewById(R.id.txt_forgot);
        prog = findViewById(R.id.prog);
        l1 = findViewById(R.id.l1);
        l2 = findViewById(R.id.l2);
        l3 = findViewById(R.id.l3);
        et_mob = findViewById(R.id.edt_mob);
        btn_get_pwd = findViewById(R.id.new_pwd);
    }

    private void Login() {
        AuthenticationApi authenticationApi = ApiClient.getClient().create(AuthenticationApi.class);
        LoginInput i = new LoginInput();
        i.setOperation("user_login");
        i.setApiKey("cda11aoip2Ry07CGWmjEqYvPguMZTkBel1V8c3XKIxwA6zQt5s");
        i.setUsername(username);
        i.setPassword(password);
        i.setPhone("");
        i.setCountryCode("");
        Call<LoginOutput> call = authenticationApi.getlogin(i);
        call.enqueue(new Callback<LoginOutput>() {
            @Override
            public void onResponse(Call<LoginOutput> call, Response<LoginOutput> response) {
                if (response.body() != null) {
                    if (response.body().getResponseStatus() == 200) {
                        userDetail = response.body().getUserDetails().get(0);
                        FeatureController.getInstance().setUid(userDetail.getId());
                        startActivity(new Intent(LoginActivity.this, AddFormData.class));
                        finish();
                    } else {
                        prog.setVisibility(View.GONE);
                        btnlogin.setVisibility(View.VISIBLE);
                        l1.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, response.body().getResponseMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    prog.setVisibility(View.GONE);
                    btnlogin.setVisibility(View.VISIBLE);
                    l1.setVisibility(View.VISIBLE);
                    Toast.makeText(LoginActivity.this, "Server Error...", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginOutput> call, Throwable t) {

                prog.setVisibility(View.GONE);
                btnlogin.setVisibility(View.VISIBLE);
                l1.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Server Error..." + t, Toast.LENGTH_LONG).show();
            }
        });

    }

    private void getNewPassword() {
        str_mob = et_mob.getText().toString();
        if (str_mob.isEmpty() || str_mob.length() != 10) {
            et_mob.setError("please enter valid mobile no.");
        } else {
            btn_get_pwd.setVisibility(View.GONE);
            prog.setVisibility(View.VISIBLE);
            AuthenticationApi authenticationApi = ApiClient.getClient().create(AuthenticationApi.class);
            ForgotPasswordInput i = new ForgotPasswordInput();
            i.setOperation("forgot_password");
            i.setApiKey("cda11aoip2Ry07CGWmjEqYvPguMZTkBel1V8c3XKIxwA6zQt5s");
            i.setEmailId("");
            i.setCountryCode("+91");
            i.setPhoneNumber(str_mob);
            Call<ForgotPasswordOutput> call = authenticationApi.sendPassword(i);
            call.enqueue(new Callback<ForgotPasswordOutput>() {
                @Override
                public void onResponse(Call<ForgotPasswordOutput> call, Response<ForgotPasswordOutput> response) {
                    if (response.body() != null) {
                        if (response.body().getResponsestatus() == 200) {
                            String str_msg = response.body().getResponceMessage();
                            Toast.makeText(LoginActivity.this, str_msg, Toast.LENGTH_LONG).show();
                            prog.setVisibility(View.GONE);
                            l2.setVisibility(View.GONE);
                            btn_get_pwd.setVisibility(View.GONE);
                            l1.setVisibility(View.VISIBLE);
                            l3.setVisibility(View.VISIBLE);
                        } else {
                            prog.setVisibility(View.GONE);
                            btn_get_pwd.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, response.body().getResponceMessage(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        prog.setVisibility(View.GONE);
                        btn_get_pwd.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Server Error...", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordOutput> call, Throwable t) {
                    btn_get_pwd.setVisibility(View.VISIBLE);
                    prog.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Server Error..." + t, Toast.LENGTH_LONG).show();
                }
            });


        }
    }


    public void onSignup(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}
