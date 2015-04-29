package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import nl.wijzijnwepps.karper.Application;
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
    private TextView activeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);

        overlay = (RelativeLayout) findViewById(R.id.overlay);
        activeText = (TextView) findViewById(R.id.active_with_text);

        helper = new SecurePreferencesHelper(this);
        emailField.setText(helper.getString("username",""));
        passwordField.setText(helper.getString("password",""));

        if(!isNetworkAvailable() && helper.getBoolean("authenticated",false)){
            startMainActivity();
        }

        if(helper.getBoolean("autoLogin",false)){
            if(helper.getBoolean("facebookLogin",false)) {
                overlay.setVisibility(View.VISIBLE);
                activeText.setText(getString(R.string.action_logging_in_facebook));
                ParseFacebookUtils.logIn(LoginActivity.this,LoginActivity.this);
                facebookLogin = true;
            }
            else {
                login();
            }
        }

        final Button loginButton = (Button) findViewById(R.id.buttonLogin);
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

        passwordField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    loginButton.performClick();

                    return true;
                }
                return false;
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
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
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
            helper.putBoolean("authenticated", true);
        } else {
            // Signup failed. Look at the ParseException to see what happened.
            try {
                new KarperDialog(this, getString(R.string.login_failed), getString(R.string.login_failed_text));
            } catch(WindowManager.BadTokenException BTE) {
                Log.e("Parse", "Login unsuccessful: " + e.getMessage());
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get tracker.
        Tracker t = ((Application) getApplication()).getTracker(
                Application.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("Login");

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

//    private boolean hasActiveInternetConnection(Context context) {
//        if (isNetworkAvailable(context)) {
//            try {
//                HttpURLConnection urlc = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
//                urlc.setRequestProperty("User-Agent", "Test");
//                urlc.setRequestProperty("Connection", "close");
//                urlc.setConnectTimeout(1500);
//                urlc.connect();
//                return (urlc.getResponseCode() == 200);
//            } catch (IOException e) {
//                Log.e("INETCHECK", "Error checking internet connection", e);
//            }
//        } else {
//            Log.d("INETCHECK", "No network available!");
//        }
//        return false;
//    }
}
