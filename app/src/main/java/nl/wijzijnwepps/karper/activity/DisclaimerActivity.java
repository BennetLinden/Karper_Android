package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;
import nl.wijzijnwepps.karper.R;

/**
 * Created by Stephan on 14/03/15.
 */
public class DisclaimerActivity extends Activity {

    private Button agree, disagree;
    private SecurePreferencesHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        agree = (Button) findViewById(R.id.agree);
        disagree = (Button) findViewById(R.id.disagree);

        helper = new SecurePreferencesHelper(DisclaimerActivity.this);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisclaimerActivity.this,MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                helper.putBoolean("disclaimerAgreed", true);
            }
        });

        disagree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisclaimerActivity.this,LoginActivity.class);
                helper.putBoolean("autoLogin", false);
                helper.putBoolean("facebookLogin",false);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                finish();
            }
        });

        WebView webView = (WebView) findViewById(R.id.disclaimer_webview);
        webView.setBackgroundColor(0x00000000);//Make transparent so the beige background set in XML is visible
        webView.loadUrl("file:///android_asset/Gebruiksvoorwaarden.htm");
    }
}
