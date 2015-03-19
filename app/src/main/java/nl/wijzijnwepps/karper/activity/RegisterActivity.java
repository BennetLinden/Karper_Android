package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;
import nl.wijzijnwepps.karper.widget.KarperDialog;


public class RegisterActivity extends Activity implements SignUpCallback {

    private EditText nameField, emailField, passwordField;
    private RelativeLayout overlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getActionBar().setTitle("Aanmelden");

        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);

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
                    ParseUser parseUser = new ParseUser();
                    parseUser.setUsername(name);
                    parseUser.setEmail(email);
                    parseUser.setPassword(password);

                    parseUser.signUpInBackground(this);
                } else {
                    new KarperDialog(this,"Leeg wachtwoord", "Het wachtwoord mag niet leeg zijn");
                }
            } else {
                new KarperDialog(this,"Geen emailadres", "Vul aub een geldig emailadres in");
            }
        } else {
            new KarperDialog(this,"Geen naam", "Vul aub een gebruikersnaam in");
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
            new KarperDialog(this, "Login mislukt", e.getMessage());
            Log.e("Parse","Register unsuccessful: "+e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }
}
