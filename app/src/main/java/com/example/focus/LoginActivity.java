package com.example.focus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {

    public static final String EXTRA_USERNAME = "com.example.focus.USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.focus.PASSWORD";
    private static final int REGISTER_REQUEST = 1;

    private EditText usernameInput;
    private EditText passwordInput;
    private TextView statusText;
    private String registeredUsername;
    private String registeredPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        statusText = findViewById(R.id.statusText);
        Button loginButton = findViewById(R.id.loginButton);
        TextView registerLink = findViewById(R.id.registerLink);

        if (savedInstanceState != null) {
            registeredUsername = savedInstanceState.getString(EXTRA_USERNAME);
            registeredPassword = savedInstanceState.getString(EXTRA_PASSWORD);
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin();
            }
        });

        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivityForResult(intent, REGISTER_REQUEST);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_USERNAME, registeredUsername);
        outState.putString(EXTRA_PASSWORD, registeredPassword);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode != REGISTER_REQUEST || resultCode != RESULT_OK || data == null) {
            return;
        }

        registeredUsername = data.getStringExtra(EXTRA_USERNAME);
        registeredPassword = data.getStringExtra(EXTRA_PASSWORD);

        if (registeredUsername != null && registeredPassword != null) {
            usernameInput.setText(registeredUsername);
            passwordInput.setText("");
            usernameInput.setError(null);
            passwordInput.setError(null);
            showMessage(getString(R.string.registration_success), true);
        }
    }

    private void checkLogin() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        usernameInput.setError(null);
        passwordInput.setError(null);

        boolean valid = true;
        if (username.isEmpty()) {
            usernameInput.setError(getString(R.string.username_required));
            valid = false;
        }
        if (password.isEmpty()) {
            passwordInput.setError(getString(R.string.password_required));
            valid = false;
        }
        if (!valid) {
            showMessage(getString(R.string.fill_required_fields), false);
            if (username.isEmpty()) {
                usernameInput.requestFocus();
            } else {
                passwordInput.requestFocus();
            }
            return;
        }

        if (registeredUsername == null || registeredPassword == null) {
            showMessage(getString(R.string.register_first), false);
            return;
        }

        if (registeredUsername.equals(username) && registeredPassword.equals(password)) {
            showMessage(getString(R.string.login_success, registeredUsername), true);
        } else {
            showMessage(getString(R.string.wrong_credentials), false);
        }
    }

    private void showMessage(String message, boolean success) {
        statusText.setText(message);
        statusText.setTextColor(getColor(success ? R.color.teal : R.color.error));
        statusText.setVisibility(View.VISIBLE);
    }
}
