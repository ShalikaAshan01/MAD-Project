package com.sadeveloper.sample_qna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.service.autofill.ImageTransformation;
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
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.apache.commons.lang3.StringUtils;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;


public class user_details_activity extends Fragment {
    private static final int GALERY_REQUEST_CODE = 1997;
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
    private StorageReference storageReference;
    private static ImageView imgProfilePicture;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
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
        imgProfilePicture = rootView.findViewById(R.id.imgProfilePicture);
        storageReference = FirebaseStorage.getInstance().getReference();
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
                work = dataSnapshot.child("work").getValue().toString().trim();
                //set values for work textview
                if (work.isEmpty() || work == null) {
                    tv_works.setText(" Add Working Place");
                    tv_works.setTextColor(Color.parseColor("#0091ea"));
                } else {
                    tv_works.setText(" Works in " + work);
                    tv_works.setTextColor(Color.parseColor("#000000"));
                }
                live = dataSnapshot.child("location").getValue().toString();
                //set value for live txt view
                if (live == null || live.isEmpty()) {
                    tv_lives.setText(" Add Location");
                    tv_lives.setTextColor(Color.parseColor("#0091ea"));
                } else {
                    tv_lives.setText(" Lives in " + live);
                    tv_lives.setTextColor(Color.parseColor("#000000"));
                }
                degree = dataSnapshot.child("degree").getValue().toString();
                //set value for degree text view
                if (degree == null || degree.isEmpty()) {
                    tv_degree.setText(" Add Degree");
                    tv_degree.setTextColor(Color.parseColor("#0091ea"));
                } else {
                    tv_degree.setText(" " + degree);
                    tv_degree.setTextColor(Color.parseColor("#000000"));
                }

                tv_username.setText(" " + firstname + " " + lastname);
//                progressBar.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity().getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        StorageReference loadImage = storageReference.child("user_picture").child(mAuth.getCurrentUser().getUid());

        loadImage.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(imgProfilePicture);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(getActivity().getApplicationContext(), "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });





        //handle buttons
        //logout button
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
                Button btnChangePicture = (Button) popupView.findViewById(R.id.btnChangePicture);

                //dismiss floating view
                btnDismiss.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                //Change user's password
                changepassword.setOnClickListener((new Button.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                LayoutInflater layoutInflater = LayoutInflater.from(user_details_activity.this.getActivity());
                                final View promptView = layoutInflater.inflate(R.layout.edit_message, null);
                                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(arg0.getContext());
                                alertDialogBuilder.setView(promptView);
                                final EditText editText = (EditText) promptView.findViewById(R.id.editTextPw);
                                final EditText editTextnew = (EditText) promptView.findViewById(R.id.editTextnewPw);
                                final EditText editTextcon = (EditText) promptView.findViewById(R.id.editTextconPw);
                                final String con = editTextcon.getText().toString().trim();
                                final String pass = editTextnew.getText().toString().trim();
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
                                                            if (!(con.equalsIgnoreCase(pass) && pass.length() >= 8)) {
                                                                progress.dismiss();
                                                                Toast.makeText(getActivity().getApplicationContext(), "Password does not meet the requirements", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                user.updatePassword(con).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                                final String newFname = StringUtils.capitalize(editTextFirstname.getText().toString().toLowerCase());
                                final String newLname = StringUtils.capitalize(editTextlastname.getText().toString().toLowerCase());
                                progress.setMessage("Updating...");
                                alertDialogBuilder
                                        .setCancelable(false)
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                progress.show();
                                                String ffname = newFname;
                                                String llname = newLname;
                                                if (newFname.isEmpty() || newFname == null) {
                                                    ffname = firstname;
                                                }
                                                if (newLname.isEmpty() || newLname == null) {
                                                    llname = lastname;
                                                }
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("firstname").setValue(ffname);
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("lastname").setValue(llname);
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
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("work").setValue(editTextwork.getText().toString());
                                                work = editTextwork.getText().toString();
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
                //change user's degree
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
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("degree").setValue(editTextDegree.getText().toString());
                                                degree = editTextDegree.getText().toString();
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
                //change profile picture
                btnChangePicture.setOnClickListener((new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, GALERY_REQUEST_CODE);

                            }
                        })
                );


                //cahnge user's location
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
                                                databaseReference.child(mAuth.getCurrentUser().getUid()).child("location").setValue(editTextLocation.getText().toString());
                                                live = editTextLocation.getText().toString();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            progress.setMessage("Uploading Profile Picture");
            progress.show();
            Uri uri = data.getData();
            StorageReference filepath = storageReference.child("user_picture").child(mAuth.getCurrentUser().getUid());
            filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progress.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Successfully Uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progress.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Cannot Upload Profile picture", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
