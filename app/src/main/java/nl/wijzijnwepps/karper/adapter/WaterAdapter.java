package nl.wijzijnwepps.karper.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 14/01/15.
 */
public class WaterAdapter extends ArrayAdapter<Water> {

    private Context context;
    private ArrayList<Water> waters;
    private int resource;

    public WaterAdapter(Context context, int resource, ArrayList<Water> waters) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.waters = waters;
    }

    @Override
    public int getCount() {
        return waters.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(resource, parent, false);
        }

        Water water = waters.get(position);

        TextView nameView = (TextView) convertView.findViewById(R.id.water_name);
        nameView.setText(water.getName());

        TextView cityView = (TextView) convertView.findViewById(R.id.city_name);
        cityView.setText(water.getCity());

        ImageView iconHengels, iconCategorie, iconBoot, iconOnline, iconNight;
        iconHengels = (ImageView) convertView.findViewById(R.id.icon_hengels);
        iconCategorie = (ImageView) convertView.findViewById(R.id.icon_categorie);
        iconBoot = (ImageView) convertView.findViewById(R.id.icon_boot);
        iconOnline = (ImageView) convertView.findViewById(R.id.icon_online);
        iconNight = (ImageView) convertView.findViewById(R.id.icon_night);

        if(water.isBootToegestaan()) iconBoot.setVisibility(View.VISIBLE);
        else iconBoot.setVisibility(View.GONE);
        if(water.isNachtvissenToegestaan()) iconNight.setVisibility(View.VISIBLE);
        else iconNight.setVisibility(View.GONE);
        if(water.isVergunningOnlineVerkrijgbaar()) iconOnline.setVisibility(View.VISIBLE);
        else iconOnline.setVisibility(View.GONE);
        if(water.getCategorie()==1) iconCategorie.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_cat_1));
        else iconCategorie.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_cat_2));

        if(water.getAantalHengels()==1) iconHengels.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_rods_1));
        else if(water.getAantalHengels()==2) iconHengels.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_rods_2));
        else if(water.getAantalHengels()==3) iconHengels.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_rods_3));
        else iconHengels.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_rods_4));

        return convertView;
    }
}
