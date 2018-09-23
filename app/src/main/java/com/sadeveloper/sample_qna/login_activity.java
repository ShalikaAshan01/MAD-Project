package com.sadeveloper.sample_qna;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class login_activity extends AppCompatActivity {

    EditText editTextUsername, editTextPassword;

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
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);//set splash screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        //if the user is already logged in we will directly start the profile activity
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, UserAreaActivity.class));
            return;
        }

        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);


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
        final String username = editTextUsername.getText().toString();
        final String password = editTextPassword.getText().toString();

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

        //if everything is fine
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject obj = new JSONObject(response);
                            //creating a new user object
                            //                    //if no error in response
                            if (!obj.getBoolean("error")) {

                                JSONObject userJson = obj.getJSONObject("user");
                                User user = new User(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email"),
                                        userJson.getString("gender"),
                                        userJson.getString("firstname"),
                                        userJson.getString("lastname")
                                );
                                //storing the user in shared preferences
                                SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);

                                //starting the main activity
                                finish();
                                startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));

                            }
                            Toast.makeText(login_activity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            Toast.makeText(login_activity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(login_activity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }


        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {

                if (progressBar != null && progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
            }
        });


//        class UserLogin extends AsyncTask<Void, Void, String> {
//
//            ProgressBar progressBar;
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                progressBar.setVisibility(View.GONE);
//
//
//                try {
//                    //converting response to json object
//                    JSONObject obj = new JSONObject(s);
//
//                    //if no error in response
//                    if (!obj.getBoolean("error")) {
//                        Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//
//                        //getting the user from the response
//                        JSONObject userJson = obj.getJSONObject("user");
//
//                        //creating a new user object
//                        User user = new User(
//                                userJson.getInt("id"),
//                                userJson.getString("username"),
//                                userJson.getString("email"),
//                                userJson.getString("gender"),
//                                userJson.getString("firstname"),
//                                userJson.getString("lastname")
//                        );
//
//                        //storing the user in shared preferences
//                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
//
//                        //starting the profile activity
//                        finish();
//                        startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("password", password);
//
//                //returning the response
//                return requestHandler.sendPostRequest(URLs.URL_LOGIN, params);
//            }
//        }
//
//        UserLogin ul = new UserLogin();
//        ul.execute();
    }
}