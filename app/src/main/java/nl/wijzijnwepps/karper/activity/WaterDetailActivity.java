package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
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
        categoryView.setText("Categorie "+water.getCategorie());

        TextView nightView = (TextView) findViewById(R.id.night);
        if(water.isNachtvissenToegestaan()) nightView.setText("Toegestaan");
        else nightView.setText("Niet toegestaan");

        TextView boatView = (TextView) findViewById(R.id.boat);
        if(water.isElectrischeBoot()) boatView.setText("Elektrische boot");
        else if(water.isBenzineBoot()) boatView.setText("Benzine boot");
        else if(water.isRoeiboot()) boatView.setText("Roeiboot");
        else if(water.isVoerboot()) boatView.setText("Voerboot");
        else boatView.setText("Niet toegestaan");

        TextView onlineView = (TextView) findViewById(R.id.online);
        if(water.isVergunningOnlineVerkrijgbaar()) onlineView.setText("Online verkrijgbaar");
        else onlineView.setText("Niet online verkrijgbaar");

        TextView beschrijving = (TextView) findViewById(R.id.description);
        if(Locale.getDefault().getLanguage().equals("nl")){
            beschrijving.setText(water.getBeschrijving());
        } else {
            beschrijving.setText(water.getBeschrijvingEN());
        }
    }
}
