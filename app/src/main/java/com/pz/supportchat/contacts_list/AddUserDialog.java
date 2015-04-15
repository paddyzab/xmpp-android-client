package com.pz.supportchat.contacts_list;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.pz.supportchat.R;

public class AddUserDialog extends DialogFragment {

    private AddContactDialogListener addContactDialogListener;

    public static AddUserDialog newInstance() {
        return new AddUserDialog();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        addContactDialogListener = (AddContactDialogListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        final View fragmentView = inflater.inflate(R.layout.add_contact_dialog, null);
        final EditText editTextUserName = (EditText) fragmentView.findViewById(R.id.editTextUserName);

        builder.setView(fragmentView)
                .setPositiveButton(R.string.add_user, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        addContactDialogListener.onFinishEditDialog(editTextUserName.getText().toString());
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddUserDialog.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }
}
