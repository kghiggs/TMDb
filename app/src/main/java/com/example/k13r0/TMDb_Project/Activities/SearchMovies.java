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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.k13r0.TMDb_Project.MainActivity;
import com.example.k13r0.TMDb_Project.R;

public class SearchMovies extends AppCompatActivity implements View.OnClickListener
{
    private EditText txtMovieSearch;
    private Button btnSearch;
    private TextView txtResults;
    private  Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtMovieSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);
        txtResults = findViewById(R.id.txtSearchResults);
        btnBack = findViewById(R.id.btnBack);

        txtMovieSearch.addTextChangedListener(searchTextWatcher);

        btnSearch.setOnClickListener(this);
        btnBack.setOnClickListener(this);
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
}
