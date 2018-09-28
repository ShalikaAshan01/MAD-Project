package com.sadeveloper.sample_qna;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.commons.lang3.StringUtils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class user_details_activity extends Fragment {
    private static Button logout;
    private FloatingActionButton fab;
    private TextView tv_questions, tv_degree, tv_works, tv_lives, tv_username, tv_email;
    static ProgressDialog progress;
    private static String degree, work, live;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private static ProgressBar progressBar;
    private static String email, username, firstname, lastname;
    private static FirebaseUser user;
    private static String tempwork, templive;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_user_details, container, false
        );
        progress = new ProgressDialog(getContext());
        logout = rootView.findViewById(R.id.btn_logout);
        fab = rootView.findViewById(R.id.fab);
        tv_questions = rootView.findViewById(R.id.tv_questions);
        tv_degree = rootView.findViewById(R.id.tv_degree);
        tv_works = rootView.findViewById(R.id.tv_works);
        tv_lives = rootView.findViewById(R.id.tv_lives);
        tv_email = rootView.findViewById(R.id.tv_email);
        tv_username = (TextView) rootView.findViewById(R.id.tv_username);

        //set text view values from database
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");
        String uid = mAuth.getCurrentUser().getUid();
        progressBar = rootView.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                username = dataSnapshot.child("username").getValue().toString();
                firstname = dataSnapshot.child("firstname").getValue().toString();
                lastname = dataSnapshot.child("lastname").getValue().toString();
                email = mAuth.getCurrentUser().getEmail();
                tv_email.setText(email);
                tv_username.setText(firstname + " " + lastname);
                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity().getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });


        tempwork = (String) tv_works.getText();
        templive = (String) tv_lives.getText();
        degree = (String) tv_degree.getText();

        work = tempwork.substring(9);
        live = templive.substring(9);

        logout.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        userLogout(getContext());
                    }
                })


        );


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

                //Change user's password
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
                                progress.setMessage("Updating Password...");
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                progress.show();
                                                user = FirebaseAuth.getInstance().getCurrentUser();
                                                AuthCredential authCredential = EmailAuthProvider.getCredential(email, editText.getText().toString().trim());
                                                //re-authenticate  user
                                                user.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            user.updatePassword(editTextcon.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                @Override
                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                    //if passaword successfully changed
                                                                    if (task.isSuccessful()) {
                                                                        progress.dismiss();
                                                                        Toast.makeText(getActivity().getApplicationContext(), "Password updated successfully", Toast.LENGTH_SHORT).show();
                                                                        userLogout(getContext());
                                                                    } else {
                                                                        progress.dismiss();
                                                                        Toast.makeText(getActivity().getApplicationContext(), "Cannot change password", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                }
                                                            });
                                                        }
                                                        //if credintial invalid
                                                        else {
                                                            progress.dismiss();
                                                            Toast.makeText(getActivity().getApplicationContext(), "Invalid Password", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
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
                //update user's name
                btnChangeName.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                final View promptView = layoutInflater.inflate(R.layout.edit_name_popup, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editTextFirstname = (EditText) promptView.findViewById(R.id.editTextFirstname);
                                final EditText editTextlastname = (EditText) promptView.findViewById(R.id.editTextlastname);
                                editTextFirstname.setHint(firstname);
                                editTextlastname.setHint(lastname);
                                progress.setMessage("Updating...");
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                progress.show();
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("firstname").setValue(StringUtils.capitalize(editTextFirstname.getText().toString().toLowerCase()));
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("lastname").setValue(StringUtils.capitalize(editTextlastname.getText().toString().toLowerCase()));
                                                progress.dismiss();
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

    //logging out user
    public void userLogout(Context context) {
        AlertDialog.Builder a_builder = new AlertDialog.Builder(context);
        a_builder.setMessage("Do you want to logout ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mAuth.signOut();
                        Intent intent = new Intent(getActivity().getApplicationContext(), login_activity.class);
                        intent.putExtra("exit", true);
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

}
