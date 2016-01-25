package com.lab.eventapp.Services;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Adam on 2016-01-25.
 */
public class ModalService
{
    public static void ShowErrorModal(String errorMsg, Activity currContext)
    {
            AlertDialog.Builder dialog = new AlertDialog.Builder(currContext);
            dialog.setMessage(errorMsg);
            dialog.setTitle("Error!");
            dialog.setPositiveButton("OK", null);
            dialog.setCancelable(true);
            dialog.create().show();
    }
    public static void ShowErrorModal(String errorMsg, Context currContext)
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(currContext);
        dialog.setMessage(errorMsg);
        dialog.setTitle("Error!");
        dialog.setPositiveButton("OK", null);
        dialog.setCancelable(true);
        dialog.create().show();
    }


    public static void ShowNoConnetionError(final Activity currActivity)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(currActivity);

        builder.setMessage("There is no internet connection. Please fix the problem and reload application.");
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                currActivity.finishAffinity();
            }
        });
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                currActivity.finishAffinity();
            }
        });
        builder.show();
    }
}
