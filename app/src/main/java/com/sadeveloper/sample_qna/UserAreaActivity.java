package com.sadeveloper.sample_qna;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.shashank.sony.fancytoastlib.FancyToast;

public class UserAreaActivity extends FragmentActivity {
    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    ViewPager viewPager;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Toolbar toolbar;
    TabLayout tabLayout;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        //if the user is not logged in
        //starting the login activity
        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    finish();
                    startActivity(new Intent(UserAreaActivity.this, login_activity.class));
                }
            }
        };
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            ViewPager viewPager = findViewById(R.id.view_pager);

            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),this));

            tabLayout.setupWithViewPager(viewPager);

            setTabIcons();

        //set tab icon on selected tabs
            tabLayout.addOnTabSelectedListener(
                    new TabLayout.OnTabSelectedListener()
                    {

                        @Override
                        public void onTabSelected(TabLayout.Tab tab) {
                            switch (tab.getPosition()){
                                case 0:
                                    tab.setIcon(R.drawable.img_home_selected);
                                    tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
                                    tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
                                    tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
                                    tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
                                    tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
                                    break;
                                case 1:
                                    tabLayout.getTabAt(0).setIcon(R.drawable.img_home_unselected);
                                    tab.setIcon(R.drawable.img_question_selected);
                                    tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
                                    tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
                                    tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
                                    tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
                                    break;
                                case 2:
                                    tabLayout.getTabAt(0).setIcon(R.drawable.img_home_unselected);
                                    tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
                                    tab.setIcon(R.drawable.img_notification_selected);
                                    tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
                                    tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
                                    tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
                                    break;
                                case 3:
                                    tabLayout.getTabAt(0).setIcon(R.drawable.img_home_unselected);
                                    tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
                                    tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
                                    tab.setIcon(R.drawable.img_search_selected);
                                    tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
                                    tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
                                    break;
                                case 4:
                                    tabLayout.getTabAt(0).setIcon(R.drawable.img_home_unselected);
                                    tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
                                    tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
                                    tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
                                    tab.setIcon(R.drawable.img_favourite_selected);
                                    tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
                                    break;

                                case 5:
                                    tabLayout.getTabAt(0).setIcon(R.drawable.img_home_unselected);
                                    tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
                                    tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
                                    tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
                                    tab.setIcon(R.drawable.img_menu_selected);
                                    tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
                                    break;
                            }

                        }

                        @Override
                        public void onTabUnselected(TabLayout.Tab tab) {

                        }

                        @Override
                        public void onTabReselected(TabLayout.Tab tab) {

                        }
                    }

            );

           setTabIcons();
    }
    public void setTabIcons(){
        tabLayout.getTabAt(0).setIcon(R.drawable.img_home_selected);
        tabLayout.getTabAt(1).setIcon(R.drawable.img_question_unselected);
        tabLayout.getTabAt(2).setIcon(R.drawable.img_notification_unselected);
        tabLayout.getTabAt(3).setIcon(R.drawable.img_search_unselected);
        tabLayout.getTabAt(4).setIcon(R.drawable.img_favourite_unselected);
        tabLayout.getTabAt(5).setIcon(R.drawable.img_menu_unselected);
    }


    //set double pressed backbutton to exit
    private boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }
        else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true;

            FancyToast.makeText(this,"Please click BACK again to exit.", FancyToast.LENGTH_SHORT,FancyToast.INFO,false).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            this.moveTaskToBack(true);
            this.finish();
            return;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }
}
