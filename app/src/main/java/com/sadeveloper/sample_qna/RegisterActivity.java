package com.sadeveloper.sample_qna;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextUsername, editTextEmail, editTextPassword,editTextConfirmPassword,editTextFirstname,editTextLastname;
    RadioGroup radioGroupGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER, new Response.Listener<String>() {
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
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                        //starting the main activity
                        finish();
                        startActivity(new Intent(getApplicationContext(), UserAreaActivity.class));
                    }
                    Toast.makeText(RegisterActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(RegisterActivity.this, "Somthing occur", Toast.LENGTH_SHORT).show();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("gender",gender);
                params.put("password",password);
                params.put("lastname",lastname);
                params.put("firstname",firstname);
                return params;
            }
        };
        final ProgressBar progressBar  =findViewById(R.id.progressBar);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressBar.setVisibility(View.VISIBLE);
        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {

                if (progressBar !=  null && progressBar.isShown())
                    progressBar.setVisibility(View.GONE);
            }
        });

//        //if it passes all the validations
//
//        class RegisterUser extends AsyncTask<Void, Void, String> {
//
//            private ProgressBar progressBar;
//
//            @Override
//            protected String doInBackground(Void... voids) {
//                //creating request handler object
//                RequestHandler requestHandler = new RequestHandler();
//
//                //creating request parameters
//                HashMap<String, String> params = new HashMap<>();
//                params.put("username", username);
//                params.put("email", email);
//                params.put("password", password);
//                params.put("gender", gender);
//                params.put("firstname",firstname);
//                params.put("lastname",lastname);
//
//                //returing the response
//                return requestHandler.sendPostRequest(URLs.URL_REGISTER, params);
//            }
//
//            @Override
//            protected void onPreExecute() {
//                super.onPreExecute();
//                //displaying the progress bar while user registers on the server
//                progressBar = (ProgressBar) findViewById(R.id.progressBar);
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            protected void onPostExecute(String s) {
//                super.onPostExecute(s);
//                //hiding the progressbar after completion
//                progressBar.setVisibility(View.GONE);
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
//                        Toast.makeText(getApplicationContext(), "Some error occurred", Toast.LENGTH_SHORT).show();
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        //executing the async task
//        RegisterUser ru = new RegisterUser();
//        ru.execute();
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
