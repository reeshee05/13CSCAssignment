package com.example.a13cscassignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private TextView signin, continuebtn;
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

        continuebtn = (Button) findViewById(R.id.signup);


        editTextuser_name = (EditText) findViewById(R.id.user_name);
        editTextemail = (EditText) findViewById(R.id.email);
        editTextpassword = (EditText) findViewById(R.id.password);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signin:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.continuebtn:
                continuebtn();
                break;
        }
    }

    private void continuebtn() {
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

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password);
                continuebtn.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            User user = new User(user_name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if(taskisSuccessful()) {
                                                Toast.makeText(RegisterUser.this, "User has been registered!", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.VISIBLE);

                                                // redirect to login layout
                                            }else{
                                                Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });
                                }else{
                                    Toast.makeText(RegisterUser.this, "Failed to register", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
            }
        }
        });

    }
}