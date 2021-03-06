package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;
import nl.wijzijnwepps.karper.widget.KarperDialog;


public class RegisterActivity extends Activity implements SignUpCallback {

    private EditText nameField, emailField, passwordField;
    private RelativeLayout overlay;
    private TextView activeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getActionBar().setTitle(getString(R.string.register_title));
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);
        activeText = (TextView) findViewById(R.id.active_with_text);

        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUp();
            }
        });

    }

    private void signUp(){
        String name, email, password;
        name = nameField.getText().toString();
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        if(!name.equals("")){
            if(!email.equals("")){
                if(!password.equals("")){
                    overlay.setVisibility(View.VISIBLE);
                    activeText.setText(getString(R.string.action_register));

                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(email);
                    parseUser.setEmail(email);
                    parseUser.setPassword(password);
                    parseUser.put("naam",name);

                    parseUser.signUpInBackground(this);
                } else {
                    new KarperDialog(this,getString(R.string.empty_password),getString(R.string.empty_password_text));
                }
            } else {
                new KarperDialog(this,getString(R.string.empty_email), getString(R.string.empty_email_text));
            }
        } else {
            new KarperDialog(this,getString(R.string.empty_name), getString(R.string.empty_name_text));
        }
    }

    private void startMainActivity(){
        Intent intent;
        SecurePreferencesHelper securePreferencesHelper = new SecurePreferencesHelper(this);
        if(securePreferencesHelper.getBoolean("disclaimerAgreed", false)){
            intent = new Intent(this,MainActivity.class);
        } else {
            intent = new Intent(this,DisclaimerActivity.class);
        }
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
    }

    //Done signing up on background
    @Override
    public void done(ParseException e) {
        overlay.setVisibility(View.GONE);
        if (e == null) {
            // Register succeeded
            startMainActivity();
        } else {
            // Sign up didn't succeed. Look at the ParseException
            // to figure out what went wrong
            new KarperDialog(this, getString(R.string.register_failed), getString(R.string.register_failed_text));
            Log.e("Parse","Register unsuccessful: "+e.getMessage());
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
