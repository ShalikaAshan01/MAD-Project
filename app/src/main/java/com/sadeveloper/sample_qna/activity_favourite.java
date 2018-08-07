package com.sadeveloper.sample_qna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class activity_favourite extends Fragment {

    public ImageButton btn1, btn2, btn3, btn4, btn5, btn6;
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
