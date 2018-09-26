package com.sadeveloper.sample_qna;

import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class user_details_activity extends Fragment {
    private static Button logout;
    private FloatingActionButton fab;
    private TextView tv_questions, tv_degree, tv_works, tv_lives, tv_username,tv_email;
    private static String degree, work, live, fname, lname;
    private static String tempwork, templive, tempname, temp[];
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private static ProgressBar progressBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_user_details, container, false
        );

        logout = rootView.findViewById(R.id.btn_logout);
        fab = rootView.findViewById(R.id.fab);
        tv_questions = rootView.findViewById(R.id.tv_questions);
        tv_degree = rootView.findViewById(R.id.tv_degree);
        tv_works = rootView.findViewById(R.id.tv_works);
        tv_lives = rootView.findViewById(R.id.tv_lives);
        tv_email = rootView.findViewById(R.id.tv_email);
        tv_username = (TextView) rootView.findViewById(R.id.tv_username);

        //set text view values from Shared preference
//        final String userid = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getId();
//        final String username = SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getFirstname() + " " +
//                SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getLastname();
//        tv_username.setText(username);
//        tv_email.setText(SharedPrefManager.getInstance(getActivity().getApplicationContext()).getUser().getEmail());

        //set text view values from database
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        String uid = mAuth.getCurrentUser().getUid();

        progressBar =rootView.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String username = dataSnapshot.child("username").getValue().toString();
                String firstname = dataSnapshot.child("firstname").getValue().toString();
                String lastname = dataSnapshot.child("lastname").getValue().toString();
                String email = mAuth.getCurrentUser().getEmail();
                tv_email.setText(email);
                tv_username.setText(firstname+" " + lastname);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        tempwork = (String) tv_works.getText();
        templive = (String) tv_lives.getText();
        tempname = (String) tv_username.getText();
        degree = (String) tv_degree.getText();

        temp = tempname.split(" ");
        fname = temp[0];
        lname = temp[1];

        work = tempwork.substring(9);
        live = templive.substring(9);


        //Alert box for log out
        logout.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(view.getContext());
                        a_builder.setMessage("Do you want to logout ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //getActivity().finish();
                                        SharedPrefManager.getInstance(getActivity().getApplicationContext()).logout();
                                        mAuth.signOut();
                                        Intent intent = new Intent(getActivity().getApplicationContext(), login_activity.class);
                                        getActivity().finish();
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);


                                        Toast.makeText(getActivity().getApplicationContext(), "Successfully Logged out", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert = a_builder.create();
                        alert.setTitle("Alert !!!");
                        alert.show();


                    }
                })


        );


        //dialog box
//        changepassword.setOnClickListener((new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
//                        View promptView = layoutInflater.inflate(R.layout.edit_message, null);
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
//                        alertDialogBuilder.setView(promptView);
//                        final EditText editText = (EditText) promptView.findViewById(R.id.editTextPw);
//                        final EditText editTextnew = (EditText) promptView.findViewById(R.id.editTextnewPw);
//                        final EditText editTextcon = (EditText) promptView.findViewById(R.id.editTextconPw);
//                        alertDialogBuilder
//                                .setCancelable(false)
//                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog, int id) {
//                                        Toast.makeText(getActivity().getApplicationContext(),"Successfully Changed",Toast.LENGTH_SHORT).show();
//                                    }
//                                })
//                                .setNegativeButton("Cancel",
//                                        new DialogInterface.OnClickListener() {
//                                            public void onClick(DialogInterface dialog, int id) {
//                                                dialog.cancel();
//                                            }
//                                        });
//
//                        AlertDialog alert = alertDialogBuilder.create();
//                        alert.setTitle("Change Password");
//                        alert.show();
//
//
//                    }
//                })
//
//
//
//
//        );


//Show Pop up menu
        fab.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                LayoutInflater layoutInflater
                        = (LayoutInflater) getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.profile_popup, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        LayoutParams.MATCH_PARENT,
                        LayoutParams.MATCH_PARENT, false);

                ImageButton btnDismiss = (ImageButton) popupView.findViewById(R.id.dismiss);
                Button changepassword = (Button) popupView.findViewById(R.id.btnChangePassword);
                Button btnChangeName = (Button) popupView.findViewById(R.id.btnChangeName);
                Button btnChangeWorkin = (Button) popupView.findViewById(R.id.btnChangeWorkin);
                Button btnChangeDegree = (Button) popupView.findViewById(R.id.btnChangeDegree);
                Button btnChangeLivesin = (Button) popupView.findViewById(R.id.btnChangeLivesin);

                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                changepassword.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                View promptView = layoutInflater.inflate(R.layout.edit_message, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editText = (EditText) promptView.findViewById(R.id.editTextPw);
                                final EditText editTextnew = (EditText) promptView.findViewById(R.id.editTextnewPw);
                                final EditText editTextcon = (EditText) promptView.findViewById(R.id.editTextconPw);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Successfully Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert = alertDialogBuilder.create();
                                alert.setTitle("Change Password");
                                popupWindow.dismiss();
                                alert.show();


                            }
                        })


                );
                btnChangeName.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                View promptView = layoutInflater.inflate(R.layout.edit_name_popup, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editTextFirstname = (EditText) promptView.findViewById(R.id.editTextFirstname);
                                final EditText editTextlastname = (EditText) promptView.findViewById(R.id.editTextlastname);
                                editTextFirstname.setHint(fname);
                                editTextlastname.setHint(lname);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Successfully Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert = alertDialogBuilder.create();
                                alert.setTitle("Change Name");
                                popupWindow.dismiss();
                                alert.show();


                            }
                        })


                );

                btnChangeWorkin.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                View promptView = layoutInflater.inflate(R.layout.edit_works_popup, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editTextwork = (EditText) promptView.findViewById(R.id.editTextwork);
                                editTextwork.setHint(work);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Successfully Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert = alertDialogBuilder.create();
                                alert.setTitle("Change Working Place");
                                popupWindow.dismiss();
                                alert.show();


                            }
                        })


                );

                btnChangeDegree.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                View promptView = layoutInflater.inflate(R.layout.edit_degree_popup, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editTextDegree = (EditText) promptView.findViewById(R.id.editTextdegree);
                                editTextDegree.setHint(degree);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Successfully Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert = alertDialogBuilder.create();
                                alert.setTitle("Change My Degree");
                                popupWindow.dismiss();
                                alert.show();


                            }
                        })
                );
                btnChangeLivesin.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                View promptView = layoutInflater.inflate(R.layout.edit_location_popup, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editTextLocation = (EditText) promptView.findViewById(R.id.editTextlocation);
                                editTextLocation.setHint(live);
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Toast.makeText(getActivity().getApplicationContext(), "Successfully Changed", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                    }
                                                });

                                AlertDialog alert = alertDialogBuilder.create();
                                alert.setTitle("Change My Location");
                                popupWindow.dismiss();
                                alert.show();


                            }
                        })


                );


                popupWindow.showAsDropDown(fab, 50, -30);

            }
        });


//        tv_questions.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Fragment question=new Questions();
//                FragmentTransaction transaction=getFragmentManager().beginTransaction();
//                transaction.replace(R.id.fragmentQuestion,question);// give your fragment container id in first parameter
//                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
//                transaction.commit();
//
//            }
//        });


        return rootView;
    }
}
