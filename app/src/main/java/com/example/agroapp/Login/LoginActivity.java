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
import com.example.agroapp.R;
import com.example.agroapp.Registration.RegistrationActivity;
import com.scwang.wave.MultiWaveHeader;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    MultiWaveHeader header, footer;
    EditText edtUsername, edtPassword;
    String username, password;
    Button btnlogin;
    FeatureController featureController;
    TextView txt_signup;
    ProgressBar prog;
    LinearLayout l1;

    private String isLoggedIn = "false";
    public static final String mobileNumber = "mobileNumber";
    public static final String pwd = "password";
    private SharedPreferences sharedpreferences;
    UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
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
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "please enter valid username & password", Toast.LENGTH_LONG).show();
                }
                else {
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
                        FeatureController.getInstance().setUserdetails(userDetail);
                        startActivity(new Intent(LoginActivity.this, AddFormData.class));
                        finish();
                    } else {
                        prog.setVisibility(View.GONE);
                        btnlogin.setVisibility(View.VISIBLE);
                        l1.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, response.body().getResponseMessage().toString(), Toast.LENGTH_LONG).show();
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
            }
        });

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
        prog = findViewById(R.id.prog);
        l1 = findViewById(R.id.l1);
    }

    public void onSignup(View view) {
        startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
    }
}
