package nl.wijzijnwepps.karper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.model.Departement;

/**
 * Created by Stephan on 14/01/15.
 */
public class DepartmentAdapter extends ArrayAdapter<Departement> {

    private Context context;
    private ArrayList<Departement> departments;
    private int resource;

    public DepartmentAdapter(Context context, int resource, ArrayList<Departement> departments) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.departments = departments;
    }

    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        TextView numberView = (TextView) convertView.findViewById(R.id.department_number);
        numberView.setText(""+departments.get(position).getID());

        TextView nameView = (TextView) convertView.findViewById(R.id.department_name);
        nameView.setText(departments.get(position).getName());

        return convertView;
    }
}
