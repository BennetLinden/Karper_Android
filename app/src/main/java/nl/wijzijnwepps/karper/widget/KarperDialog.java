package nl.wijzijnwepps.karper.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import nl.wijzijnwepps.karper.R;

/**
 * Created by Stephan on 19/03/15.
 */
public class KarperDialog {

    public KarperDialog(Context context, String title, String message){
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, context.getString(R.string.ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }
}
