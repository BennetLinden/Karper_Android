package nl.wijzijnwepps.karper.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nirhart.parallaxscroll.views.ParallaxListView;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.activity.DepartmentActivity;
import nl.wijzijnwepps.karper.adapter.DepartmentAdapter;
import nl.wijzijnwepps.karper.controller.DepartementController;
import nl.wijzijnwepps.karper.event.DepartmentsLoadedEvent;
import nl.wijzijnwepps.karper.task.ParseDepartmentJSONTask;

public class DepartmentFragment extends Fragment {

    private DepartmentAdapter departmentAdapter;
    private ParallaxListView regionList;
    private RelativeLayout overlay;

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_departments, container,
                false);

        regionList = (ParallaxListView) rootView.findViewById(R.id.region_list);
        overlay = (RelativeLayout) rootView.findViewById(R.id.overlay);
        overlay.setVisibility(View.VISIBLE);

        ImageView france = new ImageView(getActivity());
        france.setImageDrawable(getResources().getDrawable(R.drawable.france));
        france.setBackground(getResources().getDrawable(R.drawable.background_map_gradient));

        regionList.addParallaxedHeaderView(france);
        regionList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if(position!=0) {
                    Intent intent = new Intent(getActivity(), DepartmentActivity.class);
                    intent.putExtra("department", DepartementController.getInstance(getActivity()).getDepartments().get(position - 1));
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                }
            }
        });

        if(DepartementController.getInstance(getActivity()).getDepartments().size()==0){
            ParseDepartmentJSONTask parseTask = new ParseDepartmentJSONTask(getActivity());
            parseTask.execute();
        } else {
            departmentAdapter = new DepartmentAdapter(getActivity(), R.layout.list_item_department, DepartementController.getInstance(getActivity()).getDepartments());
            regionList.setAdapter(departmentAdapter);
            overlay.setVisibility(View.GONE);
        }
        return rootView;
    }

    public void onEventMainThread(DepartmentsLoadedEvent event){
        DepartementController.getInstance(getActivity()).setDepartments(event.getDepartments());
        departmentAdapter = new DepartmentAdapter(getActivity(), R.layout.list_item_department, DepartementController.getInstance(getActivity()).getDepartments());
        regionList.setAdapter(departmentAdapter);
        overlay.setVisibility(View.GONE);
    }
}
