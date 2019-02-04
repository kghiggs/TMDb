/*
 *
 * Author		: Kieron Higgs
 * Date			: Jan. 25, 2019
 * Project		: Assignment 1
 * File			: MakeSnackbar.java
 * Description	: Contains a short method used to create Snackbars.
 */

package com.example.k13r0.TMDb_Project.Classes;

import android.support.design.widget.Snackbar;
import android.view.View;

/*
 * Class		: MakeSnackbar
 * Description	: This class is used to create an Android Snackbar message.
 */
public class MakeSnackbar
{
    public static void Text(String text, View view)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}
