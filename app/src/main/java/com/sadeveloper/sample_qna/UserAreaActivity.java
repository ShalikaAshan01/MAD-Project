package com.sadeveloper.sample_qna;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

public class UserAreaActivity extends FragmentActivity {
    TextView textViewId, textViewUsername, textViewEmail, textViewGender;
    ViewPager viewPager;
    Toolbar toolbar;
    TabLayout tabLayout;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        //if the user is not logged in
        //starting the login activity
        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, login_activity.class));
        }else{
           // toolbar = findViewById(R.id.app_bar);
           // setActionBar(toolbar);
            tabLayout = (TabLayout) findViewById(R.id.tab_layout);

            ViewPager viewPager = findViewById(R.id.view_pager);

            viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager(),this));

            tabLayout.setupWithViewPager(viewPager);

            setTabIcons();

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




       /* textViewId = (TextView) findViewById(R.id.textViewId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewGender = (TextView) findViewById(R.id.textViewGender);


        //getting the current user
        User user = SharedPrefManager.getInstance(this).getUser();

        //setting the values to the textviews
        textViewId.setText(String.valueOf(user.getId()));
        textViewUsername.setText(user.getUsername());
        textViewEmail.setText(user.getEmail());
        textViewGender.setText(user.getGender());

        //when the user presses logout button
        //calling the logout method
        findViewById(R.id.buttonLogout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
            }
        });*/
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

            Toast.makeText(this,"Please click BACK again to exit.", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        } else {
            super.onBackPressed();
            return;
        }
    }
}
