package com.pz.supportchat.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.LayoutInflater;

public class DialogUtils {

    public Dialog getAddUser(final Activity context, final AlertDialog.Builder builder) {
        LayoutInflater inflater = context.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        return builder.create();
    }
}
