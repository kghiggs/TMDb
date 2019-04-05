/*
 *
 * Author		: Kyle Horsley
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: About.java
 * Description	: Generates an "About" page containing details about the app and a button linking to the TMDb website.
 */

package com.example.k13r0.TMDb_Project.Activities;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.widget.Button;

import com.example.k13r0.TMDb_Project.R;

/*
 * Class		: About
 * Description	: The About class for creating the About page.
 */
public class About extends AppCompatActivity implements View.OnClickListener
{
    private Button tmdbButton;

    /*
     * Function		: onCreate
     * Description	: Creates the About page.
     * Parameters	: Bundle savedInstanceState
     * Returns		: N/A
     */
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

        tmdbButton = findViewById(R.id.tmdbButton);

        tmdbButton.setOnClickListener(this);
    }

    /*
     * Function		: onClick
     * Description	: Launches the web browser to the TMDb website when pressed.
     * Parameters	: View v
     * Returns		: N/A
     */
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.tmdbButton:

                String url = "https://www.themoviedb.org";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
                break;
        }

    }
}
