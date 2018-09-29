package com.sadeveloper.sample_qna;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword,editTextConfirmPassword,editTextFirstname,editTextLastname;
    RadioGroup radioGroupGender;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");



        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText) findViewById(R.id.editTextConfirmPassword);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGender);
        editTextFirstname = (EditText) findViewById(R.id.editTextFirstname);
        editTextLastname = (EditText) findViewById(R.id.editTextLastname);


        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on button register
                //here we will register the user to server
                registerUser();
            }
        });

        findViewById(R.id.textViewLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if user pressed on login
                //we will open the login screen
                finish();
                startActivity(new Intent(RegisterActivity.this, login_activity.class));
            }
        });

    }

    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String firstname = editTextFirstname.getText().toString().trim();
        final String lastname = editTextLastname.getText().toString().trim();
        final String confirmpassword = editTextConfirmPassword.getText().toString().trim();

        final String gender = ((RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())).getText().toString();

        //first we will do the validations

        if (TextUtils.isEmpty(username)) {
            editTextUsername.setError("Please enter username");
            editTextUsername.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(firstname)) {
            editTextFirstname.setError("Please enter your fist name");
            editTextFirstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastname)) {
            editTextLastname.setError("Please enter your last name");
            editTextLastname.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        if(!TextUtils.equals(password,confirmpassword)){
            editTextConfirmPassword.setError("Password doesn't match");
            editTextPassword.setError("Password doesn't match");
            editTextConfirmPassword.requestFocus();
            editTextPassword.requestFocus();
            return;
        }
        if(password.length()<8){
            editTextPassword.setError("password must be at least 8 characters");
            editTextPassword.requestFocus();
            return;
        }
//
        final ProgressBar progressBar  =findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        //create authenticated user using email and password
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    String userID = mAuth.getCurrentUser().getUid();
                    databaseReference.child(userID);
                    DatabaseReference currentUser = databaseReference.child(userID);
                    //save user in user table
                    currentUser.child("firstname").setValue(StringUtils.capitalize(firstname.toLowerCase()));
                    currentUser.child("username").setValue(username);
                    currentUser.child("lastname").setValue(StringUtils.capitalize(lastname.toLowerCase()));
                    currentUser.child("gender").setValue(gender);
                    currentUser.child("work").setValue("");
                    currentUser.child("degree").setValue("");
                    currentUser.child("location").setValue("");
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
                }else{
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(RegisterActivity.this,"Can't create an Account",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
    //back button double pressed to exit
    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

}
