package com.example.agroapp.Registration;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.agroapp.Api.ApiClient;
import com.example.agroapp.Api.AuthenticationApi;
import com.example.agroapp.Login.LoginActivity;
import com.example.agroapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {
    String user_id;
    EditText full_name, state, country, address1, address2, pass, username, email, phone, logi, lati, pin, district, taluka;
    String str_full_name, str_state, str_country, str_address1, str_address2, str_str_pass,str_username, str_email, str_phone, str_logi, str_lati, str_pin, str_district, str_taluka
            ,str_cc;

    Spinner cc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
        cc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                str_cc=cc.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void init() {
        username = findViewById(R.id.usernam);
        full_name = findViewById(R.id.name);
        cc= findViewById(R.id.country_code);
        email = findViewById(R.id.email);
        address1= (EditText)findViewById(R.id.Address1);
        address2= (EditText)findViewById(R.id.village);
        state = (EditText)findViewById(R.id.state);
        taluka = (EditText)findViewById(R.id.taluka);
        pass = (EditText)findViewById(R.id.pass);
        district = (EditText)findViewById(R.id.district);
        country = (EditText)findViewById(R.id.country);
        phone = (EditText)findViewById(R.id.phone);
        logi = (EditText)findViewById(R.id.longi);
        lati = (EditText)findViewById(R.id.latit);
        pin = (EditText)findViewById(R.id.pin);

    }

    public void onRegister(View view) {

        str_full_name=full_name.getText().toString();
        str_state=state.getText().toString();
        str_country=country.getText().toString();
        str_address1=address1.getText().toString();
        str_address2=taluka.getText().toString();
        str_str_pass=pass.getText().toString();
        str_username=username.getText().toString();
        str_email=email.getText().toString();
        str_phone=phone.getText().toString();
        str_logi=logi.getText().toString();
        str_lati=lati.getText().toString();
        str_pin=pin.getText().toString();
        str_district=district.getText().toString();
        str_taluka=taluka.getText().toString();
        if (str_full_name.equals("") || str_state.equals("") || str_email.equals("") || str_username.equals("") || str_phone.length()<10 || str_taluka.equals("")){
            Toast.makeText(RegistrationActivity.this,"Please enter all the fields",Toast.LENGTH_LONG).show();
        }
        else {
            register();
        }

    }

    private void register() {
        AuthenticationApi api= ApiClient.getClient().create(AuthenticationApi.class);
        RegistartionInput i=new RegistartionInput();
        i.setAddress(address1.getText().toString());
        i.setOperation("user_registration");
        i.setApiKey("cda11aoip2Ry07CGWmjEqYvPguMZTkBel1V8c3XKIxwA6zQt5s");
        i.setFullName(full_name.getText().toString());
        i.setUsername(username.getText().toString());
        i.setPassword(pass.getText().toString());
        i.setCountryCode(str_cc);
        i.setPhone(phone.getText().toString());
        i.setEmail(email.getText().toString());
        i.setTaluka(address2.getText().toString());
        i.setVillage(address2.getText().toString());
        i.setDistric(district.getText().toString());
        i.setState(state.getText().toString());
        i.setCountry(country.getText().toString());
        i.setPincode(pin.getText().toString());
        i.setLatitude(lati.getText().toString());
        i.setLongitude(logi.getText().toString());
        Call<RegistrationOutput> call=api.getRegistred(i);
        call.enqueue(new Callback<RegistrationOutput>() {
            @Override
            public void onResponse(Call<RegistrationOutput> call, Response<RegistrationOutput> response) {
                if (response.body()!=null){
                    if (response.body().getResponsestatus()==200){
                        Toast.makeText(RegistrationActivity.this,"User Registred sucessfully",Toast.LENGTH_LONG).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }
                    else { Toast.makeText(RegistrationActivity.this,response.body().getResponceMessage().toString(),Toast.LENGTH_LONG).show();

                    }
                }
            }

            @Override
            public void onFailure(Call<RegistrationOutput> call, Throwable t) {

            }
        });




    }


}