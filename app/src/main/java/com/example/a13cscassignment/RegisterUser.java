package com.example.a13cscassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView signin, greeting, email_title, password_title, forgotpass, signup, name_title;
    private EditText editTextemail, editTextpassword, editTextuser_name;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        signin = (TextView) findViewById(R.id.signin);
        signin.setOnClickListener(this);

        signup = (TextView) findViewById(R.id.signup);
        signup.setOnClickListener(this);

        editTextuser_name = (EditText) findViewById(R.id.user_name);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.signup:
                signup();
                break;
        }
    }

    private void signup() {
        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();
        String user_name = editTextuser_name.getText().toString().trim();

        if(user_name.isEmpty()){
            editTextuser_name.setError("You're lovely name is required");
            editTextuser_name.requestFocus();
            return;
        }

        if(email.isEmpty()){
            editTextemail.setError("Email is required");
            editTextemail.requestFocus();
            return;
        }

        if(Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextemail.setError("Please provide a valid email address");
            editTextemail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextpassword.setError("Password is required");
            editTextpassword.requestFocus();
            return;
        }

        if(password.length() < 6){
            editTextpassword.setError("Min password length is 6 characters");
            editTextpassword.requestFocus();
            return;
        }

    }
}