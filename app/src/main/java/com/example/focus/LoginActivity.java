package com.example.focus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

    public static final String EXTRA_USERNAME = "com.example.focus.USERNAME";
    public static final String EXTRA_PASSWORD = "com.example.focus.PASSWORD";

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

        readRegistration();

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
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        readRegistration();
    }

    private void readRegistration() {
        registeredUsername = getIntent().getStringExtra(EXTRA_USERNAME);
        registeredPassword = getIntent().getStringExtra(EXTRA_PASSWORD);

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

        // equals משווה את התוכן של המחרוזות.
        if (registeredUsername.equals(username) && registeredPassword.equals(password)) {
            showMessage(getString(R.string.login_success, registeredUsername), true);
        } else {
            passwordInput.setError(getString(R.string.wrong_credentials));
            showMessage(getString(R.string.wrong_credentials), false);
        }
    }

    private void showMessage(String message, boolean success) {
        statusText.setText(message);
        statusText.setTextColor(getColor(success ? R.color.teal : R.color.error));
        statusText.setVisibility(View.VISIBLE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
