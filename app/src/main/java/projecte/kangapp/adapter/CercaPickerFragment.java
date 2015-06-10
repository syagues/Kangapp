package projecte.kangapp.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

/**
 * Created by sergi on 8/6/15.
 */
public class CercaPickerFragment extends DialogFragment {

    // Cerca per
    int i;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] options = {"Marca","Modelo","Tipo","Categoria"};
        // Set the dialog title
        builder.setSingleChoiceItems(options, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                i = which;
            }
        });

        return builder.create();
    }
}
