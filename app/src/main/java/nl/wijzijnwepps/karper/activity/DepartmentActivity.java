package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.adapter.WaterAdapter;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 21/01/15.
 */
public class DepartmentActivity extends Activity {

    private Departement departement;
    private WaterAdapter waterAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        departement = (Departement) getIntent().getSerializableExtra("department");

        getActionBar().setTitle(departement.getName());
        final ListView waterList = (ListView) findViewById(R.id.waterList);

        waterAdapter = new WaterAdapter(this, R.layout.list_item_water,departement.getWaters());
        waterList.setAdapter(waterAdapter);

        waterList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent detailIntent = new Intent(DepartmentActivity.this,WaterDetailActivity.class);
                detailIntent.putExtra("department",departement);
                detailIntent.putExtra("water",departement.getWaters().get(i));
                startActivity(detailIntent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
