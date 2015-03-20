package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;
import nl.wijzijnwepps.karper.widget.KarperDialog;

public class LoginActivity extends Activity implements LogInCallback {

    private EditText emailField, passwordField;
    private String email, password;
    private SecurePreferencesHelper helper;
    private RelativeLayout overlay;
    private TextView forgotPassword;
    private boolean facebookLogin = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);

        helper = new SecurePreferencesHelper(this);
        emailField.setText(helper.getString("username",""));
        passwordField.setText(helper.getString("password",""));

        if(helper.getBoolean("autoLogin",false)){
            if(helper.getBoolean("facebookLogin",false)) {
                overlay.setVisibility(View.VISIBLE);
                ParseFacebookUtils.logIn(LoginActivity.this,LoginActivity.this);
                facebookLogin = true;
            }
            else {
                login();
            }
        }

        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });

        Button facebookButton = (Button) findViewById(R.id.buttonFacebook);
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay.setVisibility(View.VISIBLE);
                TextView activeText = (TextView) findViewById(R.id.active_with_text);
                activeText.setText(getString(R.string.action_logging_in_facebook));
                ParseFacebookUtils.logIn(LoginActivity.this,LoginActivity.this);
                facebookLogin = true;
            }
        });

        forgotPassword = (TextView) findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPasswordForgotActivity();
            }
        });
    }


    private void login(){
        facebookLogin = false;
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        if(!email.equals("")){
            if(!password.equals("")){
                ParseUser.logInInBackground(email, password, this);
                TextView activeText = (TextView) findViewById(R.id.active_with_text);
                activeText.setText(getString(R.string.action_logging_in));
                overlay.setVisibility(View.VISIBLE);
            } else {
                new KarperDialog(this,getString(R.string.empty_password), getString(R.string.empty_password_text));
            }
        } else {
            new KarperDialog(this,getString(R.string.empty_email), getString(R.string.empty_email_text));
        }
    }

    public void startMainActivity(){
        Intent intent;
        SecurePreferencesHelper securePreferencesHelper = new SecurePreferencesHelper(this);
        if(securePreferencesHelper.getBoolean("disclaimerAgreed", false)){
            intent = new Intent(this,MainActivity.class);
        } else {
            intent = new Intent(this,DisclaimerActivity.class);
        }
        startActivity(intent);
        finish();
    }

    public void startRegisterActivity(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

    public void startPasswordForgotActivity(){
        Intent intent = new Intent(this,PasswordForgotActivity.class);
        startActivity(intent);
    }

    //result of login attempt
    @Override
    public void done(ParseUser parseUser, ParseException e) {
        overlay.setVisibility(View.GONE);
        if (parseUser != null) {
            startMainActivity();
            helper.putBoolean("autoLogin", true);
            helper.putBoolean("facebookLogin",facebookLogin);
            if(!facebookLogin) {
                helper.putString("username", email);
                helper.putString("password", password);
            }
        } else {
            // Signup failed. Look at the ParseException to see what happened.
            new KarperDialog(this, getString(R.string.login_failed), getString(R.string.login_failed_text));
            Log.e("Parse", "Login unsuccessful: " + e.getMessage());
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }
}
