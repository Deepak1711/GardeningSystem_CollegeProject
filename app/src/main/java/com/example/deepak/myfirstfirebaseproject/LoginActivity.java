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


public class LoginActivity extends AppCompatActivity implements ApiCallback {
    EditText edUsername, edPassword;
    TextView lblLinkSignup;
    Button btnLogin;
    ProcessFeed processFeed;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        processFeed = new ProcessFeed(this, this);
        initViews();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edUsername.getText().toString();
                String password = edPassword.getText().toString();
                if (userName.equals("") || password.equals("")) {
                    Toast.makeText(LoginActivity.this, R.string.blank_fields, Toast.LENGTH_SHORT).show();
                } else {
                    Map<String, String> params = new HashMap<>();
                    params.put(Constants.Config.KEY_USERNAME, userName);
                    params.put(Constants.Config.KEY_PASSWORD, password);
                    processFeed.loginUser(params);
                }
            }
        });
        lblLinkSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnotherActivity(RegisterActivity.class);
            }
        });
    }

    private void initViews() {
        edUsername = (EditText) findViewById(R.id.editTextUsername);
        edPassword = (EditText) findViewById(R.id.editTextPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        lblLinkSignup = (TextView) findViewById(R.id.linkSignup);
    }

    @Override
    public void onSuccess() {
        Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
        startAnotherActivity(MainActivity.class);
        finish();
    }

    private void startAnotherActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        finish();
    }
}
