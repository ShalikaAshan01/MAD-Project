package com.sadeveloper.sample_qna;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private LoginButton facebookLogin;

    //back button double pressed to exit
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
            finish();
            System.exit(0);
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//set splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        facebookLogin = findViewById(R.id.facebook_login_button);

        if (getIntent().getBooleanExtra("exit", false)) {
            finish();
        }
        //if user presses on login
        //calling the method login
        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });

        //if user presses on not registered
        findViewById(R.id.textViewRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open register screen
                finish();
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }

    private void userLogin() {
        //first getting the values
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //validating inputs
        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter your username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password");
            editTextPassword.requestFocus();
            return;
        }
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(login_activity.this, "Successfully logged in", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(login_activity.this, "email and password doesn't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        //if the user is already logged in we will directly start the profile activity
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            startActivity(new Intent(this, UserAreaActivity.class));
        }
    }
}