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
 * Description	:
 */
public class About extends AppCompatActivity implements View.OnClickListener
{

    private Button tmdbButton;
    /*
     * Function		: onCreate
     * Description	:
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

        // This code uses a WebView widget to open the browser within the app
        /*
        Intent out = new Intent(getApplicationContext(), ItemWebView.class);
        out.putExtra("link", in.getStringExtra("link"));
        startActivity(out);
        */
}
