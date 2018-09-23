package com.sadeveloper.sample_qna;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class Questions extends Fragment {
    private static Button btnPost;
    public TextView tvQ1,tvQ2,tvQ3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView =inflater.inflate(
                R.layout.fragment_questions,container,false
        );

        tvQ1 = rootView.findViewById(R.id.tvQ1);
        tvQ2 = rootView.findViewById(R.id.tvQ2);
        tvQ3 = rootView.findViewById(R.id.tvQ3);
        btnPost = rootView.findViewById(R.id.button);
        btnPost.setOnClickListener((new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder a_builder = new AlertDialog.Builder(view.getContext());
                        a_builder.setMessage("Do you want to Post this Question ?")
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
                        alert.setTitle("Post Question !!!");
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
}
