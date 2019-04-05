/*
 *
 * Author		: Kieron Higgs
 * Date			: Apr. 5th, 2019
 * Project		: Assignment 2
 * File			: MakeSnackbar.java
 * Description	: Contains a short method used to create Snackbars.
 */

package com.example.k13r0.TMDb_Project.Utilities;

import android.support.design.widget.Snackbar;
import android.view.View;

/*
 * Class		: MakeSnackbar
 * Description	: This class is used to create an Android Snackbar message.
 */
public class MakeSnackbar
{
    /*
     * Function		: ShowSnackbar
     * Description	: Shows a snackbar message onscreen consisting of the pass text string.
     * Parameters	: String text - The text intended for the Snackbar message
     *                View view - The View where the Snackbar will appear
     * Returns		: N/A
     */
    public static void ShowSnackbar(String text, View view)
    {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }
}
