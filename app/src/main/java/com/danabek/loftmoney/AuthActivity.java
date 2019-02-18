package com.danabek.loftmoney;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.android.material.button.MaterialButton;

import java.util.UUID;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthActivity extends AppCompatActivity {

    private MaterialButton enter_btn;
    private Api api;
    private static final String TAG = "AuthActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = getToken();
        if (token != null) {
            MainActivity.start(this);
            finish();
        }


        setContentView(R.layout.activity_auth);
        enter_btn = findViewById(R.id.enter_btn);
        api = ((App) getApplication()).getApi();
        enter_btn.setOnClickListener(v -> {
            auth();
        });
    }

    private void auth() {
        String userID = UUID.randomUUID().toString();
        Call<AuthResponse> call = api.auth(userID);
        call.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                AuthResponse authResponse = response.body();
                String token = authResponse.getToken();
                Log.d(TAG, "onResponse: token " + token);
                saveToken(token);

                MainActivity.start(AuthActivity.this);
                finish();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
            }
        });
    }

    private void saveToken(String token) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit()
                .putString("auth_token", token)
                .apply();
    }

    private String getToken() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        return preferences.getString("auth_token", null);

    }
}
