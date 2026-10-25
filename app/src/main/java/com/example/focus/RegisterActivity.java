package com.example.focus;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText confirmPasswordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameInput = findViewById(R.id.registerUsernameInput);
        passwordInput = findViewById(R.id.registerPasswordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        Button registerButton = findViewById(R.id.registerButton);
        TextView loginLink = findViewById(R.id.loginLink);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkRegistration();
            }
        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void checkRegistration() {
        String username = usernameInput.getText().toString().trim();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        usernameInput.setError(null);
        passwordInput.setError(null);
        confirmPasswordInput.setError(null);

        boolean valid = true;
        if (username.isEmpty()) {
            usernameInput.setError(getString(R.string.username_required));
            valid = false;
        }
        if (password.isEmpty()) {
            passwordInput.setError(getString(R.string.password_required));
            valid = false;
        } else if (password.length() < 6) {
            passwordInput.setError(getString(R.string.password_too_short));
            valid = false;
        }
        if (confirmPassword.isEmpty()) {
            confirmPasswordInput.setError(getString(R.string.confirm_password_required));
            valid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordInput.setError(getString(R.string.passwords_do_not_match));
            valid = false;
        }

        if (!valid) {
            if (usernameInput.getError() != null) {
                usernameInput.requestFocus();
            } else if (passwordInput.getError() != null) {
                passwordInput.requestFocus();
            } else {
                confirmPasswordInput.requestFocus();
            }
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(LoginActivity.EXTRA_USERNAME, username);
        intent.putExtra(LoginActivity.EXTRA_PASSWORD, password);
        setResult(RESULT_OK, intent);
        finish();
    }
}
