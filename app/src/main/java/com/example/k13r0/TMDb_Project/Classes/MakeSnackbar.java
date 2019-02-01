package com.example.k13r0.TMDb_Project.Classes;

import android.support.design.widget.Snackbar;
import android.view.View;

public class MakeSnackbar
{
    public static void Text(String text, View view)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}
