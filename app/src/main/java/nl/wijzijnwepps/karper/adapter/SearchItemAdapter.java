package nl.wijzijnwepps.karper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.SearchItem;

/**
 * Created by Stephan on 14/01/15.
 */
public class SearchItemAdapter extends ArrayAdapter<SearchItem> {

    private Context context;
    private ArrayList<SearchItem> searchItems;
    private int resource;

    public SearchItemAdapter(Context context, int resource, ArrayList<SearchItem> searchItems) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.searchItems = searchItems;
    }

    @Override
    public int getCount() {
        return searchItems.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        TextView typeView = (TextView) convertView.findViewById(R.id.search_item_type);
        if(searchItems.get(position).getType()== SearchItem.Type.DEPARTMENT) typeView.setText("Departement");
        else typeView.setText("Water");

        TextView nameView = (TextView) convertView.findViewById(R.id.search_item_name);
        if(searchItems.get(position).getType()== SearchItem.Type.DEPARTMENT) nameView.setText(searchItems.get(position).getDepartement().getName());
        else nameView.setText(searchItems.get(position).getWater().getName());

        TextView deptLabel = (TextView) convertView.findViewById(R.id.search_item_dept_label);
        TextView deptName = (TextView) convertView.findViewById(R.id.search_item_dept_name);
        TextView cityLabel = (TextView) convertView.findViewById(R.id.search_item_city_label);
        TextView cityName = (TextView) convertView.findViewById(R.id.search_item_city_name);
        if(searchItems.get(position).getType()== SearchItem.Type.WATER){
            deptName.setText(searchItems.get(position).getDepartement().getName());
            cityName.setText(searchItems.get(position).getWater().getCity());
        } else {
            deptLabel.setVisibility(View.GONE);
            deptName.setVisibility(View.GONE);
            cityLabel.setVisibility(View.GONE);
            cityName.setVisibility(View.GONE);
        }

        return convertView;
    }
}
