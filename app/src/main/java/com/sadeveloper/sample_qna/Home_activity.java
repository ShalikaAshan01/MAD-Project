package com.sadeveloper.sample_qna;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Home_activity extends Fragment {

    public static Button btnAsk;
    public TextView tvQ1,tvQ2,tvQ3;
    public ImageButton imageButton4,imageButton,imageButton3;
    private final static String snack = "Added to Favourite";
    private final static String snack1 = "Remove from Favourite";
    View view;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getContext().getTheme().applyStyle(R.style.SplashTheme, true); //set splash screen

        View rootView =inflater.inflate(
                R.layout.fragment_home,container,false
        );

 //       onButtonClickListener();
        view = rootView.findViewById(android.R.id.content);
        btnAsk = rootView.findViewById(R.id.ButtonAsk);
        imageButton4 = rootView.findViewById(R.id.imageButton4);
        imageButton = rootView.findViewById(R.id.imageButton);
        imageButton3 = rootView.findViewById(R.id.imageButton3);
        tvQ1 = rootView.findViewById(R.id.tvQ1);
        tvQ2 = rootView.findViewById(R.id.tvQ2);
        tvQ3 = rootView.findViewById(R.id.tvQ3);

        //Snackbar
        imageButton4.setOnClickListener(new View.OnClickListener() {
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
        imageButton.setOnClickListener(new View.OnClickListener() {
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

        imageButton3.setOnClickListener(new View.OnClickListener() {
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



        //Alert box
        btnAsk.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(view.getContext());
                        a_builder.setMessage("Do you want to Ask this Question ?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        getActivity().finish();
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                        AlertDialog alert= a_builder.create();
                        alert.setTitle("Alert !!!");
                        alert.show();
                    }
                })



        );

        //popup
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




        return rootView;



    }


//    public void onButtonClickListener() {
//        btnAsk = (Button) btnAsk.findViewById(R.id.ButtonAsk);
//        btnAsk.setOnClickListener(
//                new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AlertDialog.Builder a_builder = new AlertDialog.Builder(Home_activity.this);
//                        a_builder.setMessage("Do you want to Ask this Question ?")
//                                .setCancelable(false)
//                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        getActivity().finish();
//                                    }
//                                })
//                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialogInterface, int i) {
//                                        dialogInterface.cancel();
//                                    }
//                                });
//                        AlertDialog alert= a_builder.create();
//                        alert.setTitle("Alert !!!");
//                        alert.show();
//                    }
//                }
//        );
//    }
}