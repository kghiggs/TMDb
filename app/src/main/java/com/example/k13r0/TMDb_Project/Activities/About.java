/*
 *
 * Author		: Kyle Horsley
 * Date			: Feb. 3rd, 2019
 * Project		: Assignment 1
 * File			: About.java
 * Description	:
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;

import com.example.k13r0.TMDb_Project.R;

/*
 * Class		: About
 * Description	:
 */
public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width * 0.8), (int)(height * 0.4));
    }
}
