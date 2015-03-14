package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginButton = (Button) findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMainActivity();
            }
        });
        Button registerButton = (Button) findViewById(R.id.buttonRegister);
        Button facebookButton = (Button) findViewById(R.id.buttonFacebook);

    }

    public void startMainActivity(){
        Intent intent;
        SecurePreferencesHelper securePreferencesHelper = new SecurePreferencesHelper(this);
//        if(securePreferencesHelper.getBoolean("disclaimerAgreed", false)){
//            intent = new Intent(this,MainActivity.class);
//        } else {
            intent = new Intent(this,DisclaimerActivity.class);
//        }
        startActivity(intent);
    }
}
