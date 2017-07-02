package com.example.deepak.myfirstfirebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created on 12/4/17.
 */


public class RegisterActivity extends AppCompatActivity implements ApiCallback {
    EditText edUsername, edPassword, edMobile;
    Button btnRegister;
    TextView lblLinkSignup;
    ProcessFeed processFeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        processFeed = new ProcessFeed(this, this);
        initViews();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edUsername.getText().toString().equals("") || edMobile.getText().toString().equals("") || edMobile.getText().toString().equals("")) {
                    Toast.makeText(RegisterActivity.this, R.string.blank_fields, Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constants.Config.KEY_USERNAME, edUsername.getText().toString());
                    params.put(Constants.Config.KEY_PASSWORD, edPassword.getText().toString());
                    params.put(Constants.Config.KEY_PHONE, edMobile.getText().toString());
                    processFeed.registerUser(params);
                }
            }
        });

        lblLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnotherActivity(LoginActivity.class);
            }
        });
    }

    private void initViews() {
        edUsername = (EditText) findViewById(R.id.editTextUsername);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        edMobile = (EditText) findViewById(R.id.editTextPhone);
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        lblLinkSignup = (TextView) findViewById(R.id.linkSignup);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(RegisterActivity.this, R.string.registration_success, Toast.LENGTH_SHORT).show();
        startAnotherActivity(LoginActivity.class);
    }

    private void startAnotherActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }
}
