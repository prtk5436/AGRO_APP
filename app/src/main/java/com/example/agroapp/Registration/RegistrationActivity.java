package com.example.agroapp.Registration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.Login.LoginActivity;
import com.example.agroapp.R;
import com.example.agroapp.Util.CommonFunctions;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    String user_id;
    EditText full_name, state, country, address1, vaddress, pass, username, email, phone, logi, lati, pin, district, taddress;
    String str_full_name, str_state, str_country, str_address1, str_Vaddress, str_str_pass, str_username, str_email, str_phone, str_logi, str_lati, str_pin, str_district, str_Taddress, str_cc;
    ProgressBar prog;
    Spinner cc;
    LinearLayout l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        cc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_cc = cc.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init() {
        username = findViewById(R.id.usernam);
        full_name = findViewById(R.id.name);
        cc = findViewById(R.id.country_code);
        email = findViewById(R.id.email);
        address1 = (EditText) findViewById(R.id.Address1);
        vaddress = (EditText) findViewById(R.id.village);
        state = (EditText) findViewById(R.id.state);
        taddress = (EditText) findViewById(R.id.taluka);
        pass = (EditText) findViewById(R.id.pass);
        district = (EditText) findViewById(R.id.district);
        country = (EditText) findViewById(R.id.country);
        phone = (EditText) findViewById(R.id.phone);
        logi = (EditText) findViewById(R.id.longi);
        lati = (EditText) findViewById(R.id.latit);
        pin = (EditText) findViewById(R.id.pin);
        prog = findViewById(R.id.prog);
        l2 = findViewById(R.id.l2);
    }

    public void onRegister(View view) {
        str_username = username.getText().toString();
        str_full_name = full_name.getText().toString();
        str_email = email.getText().toString();
        str_str_pass = pass.getText().toString();
        str_phone = phone.getText().toString();
        str_address1 = address1.getText().toString();
        str_Vaddress = vaddress.getText().toString();
        str_Taddress = taddress.getText().toString();
        str_district = district.getText().toString();
        str_state = state.getText().toString();
        str_country = country.getText().toString();
        str_pin = pin.getText().toString();
        str_lati = lati.getText().toString();
        str_logi = logi.getText().toString();
        if (str_username.equals("") || str_full_name.equals("") || str_email.equals("") || str_str_pass.equals("") || str_phone.length() != 10
                || str_Vaddress.equals("") || str_Taddress.equals("") || str_district.equals("") || str_state.equals("") || str_country.equals("") || str_pin.equals("")) {
            Toast.makeText(RegistrationActivity.this, "Please enter all valid details", Toast.LENGTH_LONG).show();
        } else {
            register();
        }

    }

    private void register() {
        l2.setVisibility(View.GONE);
        prog.setVisibility(View.VISIBLE);
        AuthenticationApi api = ApiClient.getClient().create(AuthenticationApi.class);
        RegistartionInput i = new RegistartionInput();
        i.setOperation("user_registration");
        i.setApiKey("cda11aoip2Ry07CGWmjEqYvPguMZTkBel1V8c3XKIxwA6zQt5s");
        i.setUsername(str_username);
        i.setFullName(str_full_name);
        i.setEmail(str_email);
        i.setPassword(str_str_pass);
        i.setCountryCode("+91");
        i.setPhone(str_phone);
        i.setAddress(str_address1);
        i.setVillage(str_Vaddress);
        i.setTaluka(str_Taddress);
        i.setDistric(str_district);
        i.setState(str_state);
        i.setCountry(str_country);
        i.setPincode(str_pin);
        i.setLatitude(str_lati);
        i.setLongitude(str_logi);
        Call<RegistrationOutput> call = api.getRegistred(i);
        call.enqueue(new Callback<RegistrationOutput>() {
            @Override
            public void onResponse(Call<RegistrationOutput> call, Response<RegistrationOutput> response) {

                String error_desc = response.body().getResponceMessage();
                if (response.body() != null) {
                    if (response.body().getResponsestatus() == 200) {
                        Toast.makeText(RegistrationActivity.this, "User Registred sucessfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        prog.setVisibility(View.GONE);
                        l2.setVisibility(View.VISIBLE);
                        Toast.makeText(RegistrationActivity.this, response.body().getResponceMessage(), Toast.LENGTH_LONG).show();
                        String apiname = "core/Version3/login_student_V4.php";
                        CommonFunctions.errorLog("AgroTech", "Registration credentials error", "user cannot registered", "Registration failed", "", "", str_full_name, "", "", "", "Aniket and Pramod khandare", apiname, "login.php", error_desc, "");
                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationOutput> call, Throwable t) {
                prog.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                Toast.makeText(RegistrationActivity.this, t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onCancel(View view) {
        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        finish();
    }
}