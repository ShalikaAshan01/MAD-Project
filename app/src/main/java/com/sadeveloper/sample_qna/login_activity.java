package com.sadeveloper.sample_qna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.Set;

public class login_activity extends AppCompatActivity {

    private EditText editTextUsername, editTextPassword;
    private TextView textViewForgotPassword;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    //facebook login
    private CallbackManager mCallbackManager;
    private final static String TAG = "Face";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//set splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        textViewForgotPassword = findViewById(R.id.textViewForgotPassword);

        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.facebook_login_button);
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Toast.makeText(login_activity.this, "Access denied by user", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                FancyToast.makeText(login_activity.this, "Facebook Error", FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();


                // ...
            }
        });
        if (getIntent().getBooleanExtra("exit", false)) {
            finish();
        }
        //if user press on forgottent password
        final Context context = this;

        textViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                LayoutInflater layoutInflater = LayoutInflater.from(context);
                final View promptView = layoutInflater.inflate(R.layout.edit_degree_popup, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
                alertDialogBuilder.setView(promptView);

                final EditText editText = promptView.findViewById(R.id.editTextdegree);
                editText.setHint("Enter your E-mail Adresss");

                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                final String email = editText.getText().toString().trim();
                                if((!email.isEmpty())){
                                    final ProgressDialog progressDialog = new ProgressDialog(context);
                                    progressDialog.setMessage("Sending email...");
                                    progressDialog.show();
                                    System.out.println(email);
                                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            FancyToast.makeText(getBaseContext(),"Password Reset email send to your account",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            FancyToast.makeText(getBaseContext(),"Cannot Send Email",FancyToast.LENGTH_SHORT,FancyToast.WARNING,false).show();

                                        }
                                    });
                                }
                                else{
                                    FancyToast.makeText(getBaseContext(),"Please enter valid email address",FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();

                                }
                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });


                AlertDialog alert = alertDialogBuilder.create();
                alert.setTitle("Find Your Account ");
                alert.show();

            }
        });


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
                    FancyToast.makeText(login_activity.this, "Successfully logged in", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                    progressBar.setVisibility(View.GONE);
                    finish();
                    startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
                } else {
                    progressBar.setVisibility(View.GONE);
                    FancyToast.makeText(login_activity.this, "email and password doesn't match", FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                }
            }
        });
    }

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
        FancyToast.makeText(this, "Please click BACK again to exit", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleFacebookAccessToken(final AccessToken token) {
        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Signing...");
        progressDialog.show();


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            userLoginFromFacebook(token);
                            finish();
                            startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
                            FancyToast.makeText(login_activity.this, "Successfully logged in", FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,false).show();
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            FancyToast.makeText(login_activity.this, "Authentication failed.",
                                    FancyToast.LENGTH_SHORT,FancyToast.ERROR,false).show();
                        }

                    }
                });
    }

    private void userLoginFromFacebook(AccessToken token) {
        GraphRequest request = GraphRequest.newMeRequest(
                token,
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject object,
                            GraphResponse response) {
                        try {
                            final String username = object.getString("name");
                            final String firstname = object.getString("first_name");
                            final String lastname = object.getString("last_name");
                            final String gender = object.getString("gender");
                            final JSONObject hometown = object.getJSONObject("hometown");
                            final String location = hometown.getString("name");

                            final DatabaseReference currentUser = databaseReference.child(mAuth.getCurrentUser().getUid());

                            ValueEventListener valueEventListener = new ValueEventListener() {

                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.exists()) {
                                        //save user in user table
                                        currentUser.child("firstname").setValue(StringUtils.capitalize(firstname.toLowerCase()));
                                        currentUser.child("username").setValue(username);
                                        currentUser.child("lastname").setValue(StringUtils.capitalize(lastname.toLowerCase()));
                                        currentUser.child("gender").setValue(gender);
                                        currentUser.child("work").setValue("");
                                        currentUser.child("degree").setValue("");
                                        currentUser.child("location").setValue(location);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            };
                            currentUser.addListenerForSingleValueEvent(valueEventListener);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,email,last_name,hometown,gender");
        request.setParameters(parameters);
        request.executeAsync();
    }
}