package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.adapter.DepartmentAdapter;
import nl.wijzijnwepps.karper.controller.DepartementController;
import nl.wijzijnwepps.karper.event.DepartmentsLoadedEvent;
import nl.wijzijnwepps.karper.task.CSVParseTask;

public class LoginActivity extends Activity {

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
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
    }
}
