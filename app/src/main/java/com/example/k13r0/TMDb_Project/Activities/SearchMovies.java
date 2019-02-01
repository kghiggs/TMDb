package com.example.k13r0.TMDb_Project.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.example.k13r0.TMDb_Project.R;

public class SearchMovies extends AppCompatActivity
{
    private EditText txtMovieSearch;
    private Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        txtMovieSearch = findViewById(R.id.txtSearch);
        btnSearch = findViewById(R.id.btnSearch);

        txtMovieSearch.addTextChangedListener(searchTextWatcher);
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
}
