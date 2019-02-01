/*
 *
 * Author		: Arie Kraayenbrink
 * Date			: Jan. 31, 2019
 * Project		: Assignment 1
 * File			: SearchMovies.java
 * Description	:
 * Credit		:
 */



package com.example.k13r0.TMDb_Project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.k13r0.TMDb_Project.MainActivity;
import com.example.k13r0.TMDb_Project.R;

public class SearchMovies extends AppCompatActivity implements View.OnClickListener
{
    private EditText txtMovieSearch;
    private Button btnSearch;
    private TextView txtResults;
    private Button btnBack;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtMovieSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtResults = findViewById(R.id.txtSearchResults);
        btnBack = findViewById(R.id.btnBack);
        toolbar = findViewById(R.id.toolbar);

        //setSupportActionBar(toolbar);

        txtMovieSearch.addTextChangedListener(searchTextWatcher);

        btnSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.back:
                goToMain();
                break;
            case R.id.info:
                goToAbout();

        }

        return super.onOptionsItemSelected(item);
    }



    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String searchInput = txtMovieSearch.getText().toString().trim();

            btnSearch.setEnabled(!searchInput.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnBack:
                goToMain();
                break;

            case R.id.btnSearch:
                txtResults.setVisibility(View.VISIBLE);
                break;
        }
    }



    private void goToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    private void goToAbout() {
        Intent intent = new Intent(this, About.class);
        startActivity(intent);
    }
}
