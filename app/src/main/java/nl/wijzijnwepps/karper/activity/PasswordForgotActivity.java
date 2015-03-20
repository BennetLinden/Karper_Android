package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.widget.KarperDialog;


public class PasswordForgotActivity extends Activity implements RequestPasswordResetCallback {

    private EditText emailField;
    private RelativeLayout overlay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getActionBar().setTitle(getString(R.string.title_forgot));

        emailField = (EditText) findViewById(R.id.emailField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);
        overlay.setVisibility(View.GONE);

        Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay.setVisibility(View.VISIBLE);
                TextView activeText = (TextView) findViewById(R.id.active_with_text);
                activeText.setText(getString(R.string.action_sending_email));
                ParseUser.requestPasswordResetInBackground(emailField.getText().toString(),PasswordForgotActivity.this);
            }
        });
    }

    @Override
    public void done(ParseException e) {
        overlay.setVisibility(View.GONE);

        if (e == null) {
            // An email was successfully sent with reset
            // instructions.
           new KarperDialog(this,getString(R.string.success),getString(R.string.email_sent));
        } else {
            // Something went wrong. Look at the ParseException
            // to see what's up.
            new KarperDialog(this,getString(R.string.fail),getString(R.string.email_not_sent));
        }
    }
}
