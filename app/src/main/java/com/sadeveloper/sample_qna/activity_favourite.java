package com.sadeveloper.sample_qna;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class activity_favourite extends Fragment {

    public ImageButton btn1, btn2, btn3, btn4, btn5, btn6;
    public TextView tvQ1,tvQ2,tvQ3,tvQ4,tvQ5,tvQ6;
    public FloatingActionButton fab;
    String txt = "Removed from the list";

    String snack = "Removed from the list";

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_favourite, container, false
        );


        view = rootView.findViewById(android.R.id.content);

        btn1 = rootView.findViewById(R.id.imageButton2);
        btn2 = rootView.findViewById(R.id.imageButton3);
        btn3 = rootView.findViewById(R.id.imageButton4);
        btn4 = rootView.findViewById(R.id.imageButton5);
        btn5 = rootView.findViewById(R.id.imageButton6);
        btn6 = rootView.findViewById(R.id.imageButton7);
        fab = rootView.findViewById(R.id.floatingActionButton);

        tvQ1 = rootView.findViewById(R.id.tvQ1);
        tvQ2 = rootView.findViewById(R.id.tvQ2);
        tvQ3 = rootView.findViewById(R.id.tvQ3);
        tvQ4 = rootView.findViewById(R.id.tvQ4);
        tvQ5 = rootView.findViewById(R.id.tvQ5);
        tvQ6 = rootView.findViewById(R.id.tvQ6);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, snack, Snackbar.LENGTH_SHORT)
                        .setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar.make(view, "Undo Successful!!", Snackbar.LENGTH_SHORT)
                                        .show();
                            }
                        })
                        .show();
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view,snack,Snackbar.LENGTH_SHORT).show();
                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

                //setTitle
                alertDialogBuilder.setTitle("Create a new list");

                //create a edit text
                final EditText editText = new EditText(getContext());
                editText.setInputType(InputType.TYPE_CLASS_TEXT);//text input type
                editText.setHint("List name");


                alertDialogBuilder.setView(editText);//run edit text



                alertDialogBuilder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

                //setDialogMsg


                AlertDialog alertDialog = alertDialogBuilder.create();

                //show
                alertDialog.show();
            }
        });


        tvQ1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ1, 50, -30);
            }
        });


        tvQ2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ2, 50, -30);
            }
        });


        tvQ3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ3, 50, -30);
            }
        });


        tvQ4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ4, 50, -30);
            }
        });


        tvQ5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ1, 50, -30);
            }
        });


        tvQ6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                LayoutInflater layoutInflater
                        = (LayoutInflater)getActivity().getApplicationContext()
                        .getSystemService(LAYOUT_INFLATER_SERVICE);
                final View popupView = layoutInflater.inflate(R.layout.activity_answer, null);
                final PopupWindow popupWindow = new PopupWindow(
                        popupView,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,false);
                ImageButton answerDismiss = (ImageButton) popupView.findViewById(R.id.answerDismiss);

                answerDismiss.setOnClickListener(new Button.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        popupWindow.dismiss();
                    }
                });

                popupWindow.showAsDropDown(tvQ6, 50, -30);
            }
        });










//        btn1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//
//        btn2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_LONG).show();
//            }
//        });
//
//        btn4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn5.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        btn6.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(FavouriteActivity.this,txt,Toast.LENGTH_SHORT).show();
//            }
//        });


        return rootView;
    }
}
