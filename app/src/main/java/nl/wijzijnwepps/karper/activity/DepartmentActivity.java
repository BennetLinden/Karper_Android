package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.Application;
import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.adapter.WaterAdapter;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 21/01/15.
 */
public class DepartmentActivity extends Activity {

    private Departement departement;
    private ArrayList<Water> waters;
    private ListView waterList;
    private WaterAdapter waterAdapter;
    private EditText searchField;
    private ImageView searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        departement = (Departement) getIntent().getSerializableExtra("department");

        getActionBar().setTitle(departement.getName());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        waterList = (ListView) findViewById(R.id.waterList);

        waters = new ArrayList<Water>();
        waters.addAll(departement.getWaters());

        waterAdapter = new WaterAdapter(this, R.layout.list_item_water,waters);
        waterList.setAdapter(waterAdapter);

        waterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(DepartmentActivity.this,WaterDetailActivity.class);
                detailIntent.putExtra("department",departement);
                detailIntent.putExtra("water",waters.get(i));
                startActivity(detailIntent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        TextView departmentNumber = (TextView) findViewById(R.id.department_number);
        departmentNumber.setText(""+departement.getID());

        TextView departmentName = (TextView) findViewById(R.id.department_name);
        departmentName.setText(departement.getName());

        String strWatersFormat = getResources().getString(R.string.department_water_count);
        String strWatersMsg = String.format(strWatersFormat, waterAdapter.getCount());

        TextView departmentDescr = (TextView) findViewById(R.id.department_descr);
        departmentDescr.setText(strWatersMsg);

        searchField = (EditText) findViewById(R.id.search_field);
        searchButton = (ImageView) findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchWater(searchField.getText().toString());
            }
        });

        searchField.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchButton.performClick();
                    return true;
                }
                return false;
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

    private void searchWater(String name){
        waters.clear();
        if(name.equals("")){
            waters.addAll(departement.getWaters());
        } else {
            for(Water water : departement.getWaters()){
                if(water.getName().toLowerCase().contains(name.toLowerCase()) || water.getCity().toLowerCase().contains(name.toLowerCase())){
                    waters.add(water);
                }
            }
        }
        waterAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Get tracker.
        Tracker t = ((Application) getApplication()).getTracker(
                Application.TrackerName.APP_TRACKER);

        // Set screen name.
        t.setScreenName("Department");
        t.set("Department",departement.getName());

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());
    }

}
