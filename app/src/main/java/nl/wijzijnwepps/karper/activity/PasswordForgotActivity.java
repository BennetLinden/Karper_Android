package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
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
    private TextView activeText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        getActionBar().setTitle(getString(R.string.title_forgot));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        emailField = (EditText) findViewById(R.id.emailField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);
        overlay.setVisibility(View.GONE);
        activeText = (TextView) findViewById(R.id.active_with_text);

        Button resetButton = (Button) findViewById(R.id.buttonReset);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay.setVisibility(View.VISIBLE);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return true;
    }
}
