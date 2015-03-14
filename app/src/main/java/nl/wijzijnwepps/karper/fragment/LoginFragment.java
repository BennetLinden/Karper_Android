package nl.wijzijnwepps.karper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.widget.LoginButton;

import java.util.Arrays;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.activity.DisclaimerActivity;
import nl.wijzijnwepps.karper.activity.MainActivity;
import nl.wijzijnwepps.karper.activity.RegisterActivity;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;


public class LoginFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        Button loginButton = (Button) view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
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

        return view;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        Log.i("trace", "result");
    }
}
