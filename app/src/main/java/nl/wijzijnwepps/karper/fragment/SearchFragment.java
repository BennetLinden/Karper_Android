package nl.wijzijnwepps.karper.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.activity.DepartmentActivity;
import nl.wijzijnwepps.karper.activity.WaterDetailActivity;
import nl.wijzijnwepps.karper.adapter.DepartmentAdapter;
import nl.wijzijnwepps.karper.adapter.SearchItemAdapter;
import nl.wijzijnwepps.karper.controller.DepartementController;
import nl.wijzijnwepps.karper.controller.SearchController;
import nl.wijzijnwepps.karper.event.ItemsFoundEvent;
import nl.wijzijnwepps.karper.model.SearchItem;

public class SearchFragment extends Fragment {

    private SearchItemAdapter searchItemAdapter;
    private ListView resultList;
    private EditText searchInput;
    private RelativeLayout overlay;
    private TextView activeText;

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
        View rootView = inflater.inflate(R.layout.fragment_search, container,
                false);

        overlay = (RelativeLayout) rootView.findViewById(R.id.overlay);
        overlay.setVisibility(View.GONE);

        activeText = (TextView) rootView.findViewById(R.id.active_with_text);

        resultList = (ListView) rootView.findViewById(R.id.result_list);
        resultList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent;
                SearchItem item = SearchController.getInstance(getActivity()).getResults().get(position);
                if(item.getType()== SearchItem.Type.DEPARTMENT){
                    intent = new Intent(getActivity(), DepartmentActivity.class);
                    intent.putExtra("department", item.getDepartement());
                } else {
                    intent = new Intent(getActivity(), WaterDetailActivity.class);
                    intent.putExtra("department",item.getDepartement());
                    intent.putExtra("water",item.getWater());
                }
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });

        searchInput = (EditText) rootView.findViewById(R.id.search_field);
        final ImageView searchButton = (ImageView) rootView.findViewById(R.id.search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay.setVisibility(View.VISIBLE);
                activeText.setText(getActivity().getString(R.string.action_searching) + searchInput.getText().toString());
                SearchController.getInstance(getActivity()).search(searchInput.getText().toString());
            }
        });

        return rootView;
    }

    public void onEventMainThread(ItemsFoundEvent event){
        overlay.setVisibility(View.GONE);
        searchItemAdapter = new SearchItemAdapter(getActivity(), R.layout.list_item_search_result, event.getItems());
        resultList.setAdapter(searchItemAdapter);
    }
}
