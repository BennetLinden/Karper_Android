package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 21/01/15.
 */
public class WaterDetailActivity extends Activity {

    private Water water;
    private Departement department;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_detail);

        department = (Departement) getIntent().getSerializableExtra("department");
        water = (Water) getIntent().getSerializableExtra("water");

        getActionBar().setTitle(water.getName());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        ImageView iconHengels, iconCategorie, iconBoot, iconOnline, iconNight;
        iconHengels = (ImageView) findViewById(R.id.icon_hengels);
        iconCategorie = (ImageView) findViewById(R.id.icon_categorie);
        iconBoot = (ImageView) findViewById(R.id.icon_boot);
        iconOnline = (ImageView) findViewById(R.id.icon_online);
        iconNight = (ImageView) findViewById(R.id.icon_night);

        if(water.isBootToegestaan()) iconBoot.setVisibility(View.VISIBLE);
        else iconBoot.setVisibility(View.GONE);
        if(water.isNachtvissenToegestaan()) iconNight.setVisibility(View.VISIBLE);
        else iconNight.setVisibility(View.GONE);
        if(water.isVergunningOnlineVerkrijgbaar()) iconOnline.setVisibility(View.VISIBLE);
        else iconOnline.setVisibility(View.GONE);
        if(water.getCategorie()==1) iconCategorie.setImageDrawable(getResources().getDrawable(R.drawable.icon_cat_1));
        else iconCategorie.setImageDrawable(getResources().getDrawable(R.drawable.icon_cat_2));

        if(water.getAantalHengels()==1) iconHengels.setImageDrawable(getResources().getDrawable(R.drawable.icon_rods_1));
        else if(water.getAantalHengels()==2) iconHengels.setImageDrawable(getResources().getDrawable(R.drawable.icon_rods_2));
        else if(water.getAantalHengels()==3) iconHengels.setImageDrawable(getResources().getDrawable(R.drawable.icon_rods_3));
        else iconHengels.setImageDrawable(getResources().getDrawable(R.drawable.icon_rods_4));

        TextView departmentView = (TextView) findViewById(R.id.department);
        departmentView.setText(department.getName());

        TextView cityView = (TextView) findViewById(R.id.city);
        cityView.setText(water.getCity());

        TextView hectareView = (TextView) findViewById(R.id.hectare);
        hectareView.setText(water.getHectare());

        TextView categoryView = (TextView) findViewById(R.id.category);
        categoryView.setText(getString(R.string.category)+water.getCategorie());

        TextView nightView = (TextView) findViewById(R.id.night);
        if(water.isNachtvissenToegestaan()) nightView.setText(getString(R.string.allowed));
        else nightView.setText(getString(R.string.not_allowed));

        TextView boatView = (TextView) findViewById(R.id.boat);
        String boatText = "";
        Boolean boatSpecified = false;
        if(water.isElectrischeBoot()){
            boatText += getString(R.string.boat_electric);
            boatSpecified = true;
        }
        if(water.isBenzineBoot()){
            boatText += getString(R.string.boat_fuel);
            boatSpecified = true;
        }
        if(water.isRoeiboot()){
            boatText += getString(R.string.boat_row);
            boatSpecified = true;
        }
        if(water.isVoerboot()){
            boatText += getString(R.string.boat_feeding);
            boatSpecified = true;
        }

        if(boatSpecified) boatView.setText(boatText);
        else if(water.isBootToegestaan()) boatView.setText(getString(R.string.allowed_unspecified));
        else boatView.setText(getString(R.string.not_allowed));

        TextView onlineView = (TextView) findViewById(R.id.online);
        if(water.isVergunningOnlineVerkrijgbaar()) onlineView.setText(getString(R.string.licence_online));
        else onlineView.setText(getString(R.string.licence_not_online));

        TextView beschrijving = (TextView) findViewById(R.id.description);
        beschrijving.setText(water.getBeschrijving());

        Button feedbackButton = (Button) findViewById(R.id.button_feedback);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                        "mailto", getString(R.string.feedback_email_address), null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Feedback: "+department.getName()+", "+water.getCity()+", "+water.getName());
                startActivity(Intent.createChooser(emailIntent, getString(R.string.title_send_feedback_with)));
            }
        });
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
