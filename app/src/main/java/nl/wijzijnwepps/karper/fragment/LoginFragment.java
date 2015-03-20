package nl.wijzijnwepps.karper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.widget.LoginButton;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import java.util.Arrays;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.activity.DisclaimerActivity;
import nl.wijzijnwepps.karper.activity.MainActivity;
import nl.wijzijnwepps.karper.activity.RegisterActivity;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;
import nl.wijzijnwepps.karper.widget.KarperDialog;


public class LoginFragment extends Fragment implements LogInCallback {

    private EditText emailField, passwordField;
    private String email, password;
    private SecurePreferencesHelper helper;
    private RelativeLayout overlay;
    private TextView forgotPassword;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        emailField = (EditText) view.findViewById(R.id.emailField);
        passwordField = (EditText) view.findViewById(R.id.passwordField);

        overlay = (RelativeLayout) view.findViewById(R.id.overlay);

        helper = new SecurePreferencesHelper(getActivity());
        emailField.setText(helper.getString("username",""));
        passwordField.setText(helper.getString("password",""));

        if(helper.getBoolean("autoLogin",false)){
            login();
        }

        Button loginButton = (Button) view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Button registerButton = (Button) view.findViewById(R.id.buttonRegister);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegisterActivity();
            }
        });

        LoginButton facebookButton = (LoginButton) view.findViewById(R.id.buttonFacebook);
        facebookButton.setReadPermissions(Arrays.asList("public_profile"));
        facebookButton.setFragment(this);

        forgotPassword = (TextView) view.findViewById(R.id.forgot);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, new ForgotPasswordFragment())
                        .addToBackStack("ForgotPasswordFragment")
                        .commit();
            }
        });

        return view;
    }

    private void login(){
        email = emailField.getText().toString();
        password = passwordField.getText().toString();

        if(!email.equals("")){
            if(!password.equals("")){
                ParseUser.logInInBackground(email,password,this);
                overlay.setVisibility(View.VISIBLE);
            } else {
                new KarperDialog(getActivity(),getActivity().getString(R.string.empty_password), getActivity().getString(R.string.empty_password_text));
            }
        } else {
            new KarperDialog(getActivity(),getActivity().getString(R.string.empty_email), getActivity().getString(R.string.empty_email_text));
        }
    }

    public void startMainActivity(){
        Intent intent;
        SecurePreferencesHelper securePreferencesHelper = new SecurePreferencesHelper(getActivity());
        if(securePreferencesHelper.getBoolean("disclaimerAgreed", false)){
            intent = new Intent(getActivity(),MainActivity.class);
        } else {
            intent = new Intent(getActivity(),DisclaimerActivity.class);
        }
        startActivity(intent);
    }

    public void startRegisterActivity(){
        Intent intent = new Intent(getActivity(),RegisterActivity.class);
        startActivity(intent);
    }

    //result of login attempt
    @Override
    public void done(ParseUser parseUser, ParseException e) {
        overlay.setVisibility(View.GONE);
        if (parseUser != null) {
            startMainActivity();
            helper.putBoolean("autoLogin", true);
            helper.putString("username",email);
            helper.putString("password",password);
        } else {
            // Signup failed. Look at the ParseException to see what happened.
            new KarperDialog(getActivity(), getActivity().getString(R.string.login_failed), getActivity().getString(R.string.login_failed_text));
            Log.e("Parse","Login unsuccessful: "+e.getMessage());
        }
    }
}
