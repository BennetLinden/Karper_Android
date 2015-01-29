package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.adapter.DepartmentAdapter;
import nl.wijzijnwepps.karper.controller.DepartementController;
import nl.wijzijnwepps.karper.event.DepartmentsLoadedEvent;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.task.CSVParseTask;
import nl.wijzijnwepps.karper.task.ParseDepartmentJSONTask;

public class MainActivity extends Activity {

    private DepartmentAdapter departmentAdapter;
    private ParallaxListView regionList;

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        regionList = (ParallaxListView) findViewById(R.id.region_list);

        ImageView france = new ImageView(this);
        france.setImageDrawable(getResources().getDrawable(R.drawable.france));
        france.setBackground(getResources().getDrawable(R.drawable.background_map_gradient));

        regionList.addParallaxedHeaderView(france);
        regionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position!=0) {
                    Intent intent = new Intent(MainActivity.this, DepartmentActivity.class);
                    intent.putExtra("department", DepartementController.getInstance(MainActivity.this).getDepartments().get(position - 1));
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });

        ParseDepartmentJSONTask parseTask = new ParseDepartmentJSONTask(this);
        parseTask.execute();
    }

    public void onEventMainThread(DepartmentsLoadedEvent event){
        DepartementController.getInstance(this).setDepartments(event.getDepartments());
        departmentAdapter = new DepartmentAdapter(this, R.layout.list_item_department, DepartementController.getInstance(this).getDepartments());
        regionList.setAdapter(departmentAdapter);
    }
}
