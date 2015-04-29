package nl.wijzijnwepps.karper.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Locale;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.event.DepartmentsLoadedEvent;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 14/01/15.
 */
public class ParseDepartmentJSONTask extends AsyncTask<Void,Void,ArrayList<Departement>>{

    private Context context;

    public ParseDepartmentJSONTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Departement> doInBackground(Void... voids) {
        ArrayList<Departement> departments = new ArrayList<Departement>();
        try {
            JSONArray departmentsArray = new JSONArray(loadJSONFromAsset());
            for(int i = 0; i< departmentsArray.length();i++){

                JSONObject entry = departmentsArray.getJSONObject(i);

                //Departments
                Departement dept = null;

                boolean exists = false;
                for(Departement d : departments){
                    if(entry.optInt("Nr")== d.getID()) exists=true;
                    dept = d;
                }
                if(!exists){
                    dept = new Departement(entry.optInt("Nr"),entry.getString("Departement"));
                    if(dept.getID()!=0) departments.add(dept);
                }

                //Parse the water
                Water water = new Water();
                String rivier = entry.getString("Rivier");
                String meer = entry.getString("Meer");
                String kanaal = entry.getString("Kanaal");
                if(!rivier.equals("")){
                    water.setName(rivier);
                    water.setType("rivier");
                }
                else if(!meer.equals("")){
                    water.setName(meer);
                    water.setType("meer");
                }
                else if(!kanaal.equals("")){
                    water.setName(kanaal);
                    water.setType("kanaal");
                }
                water.setCity(entry.getString("Stad"));

                //Add features to water
                if(entry.getString("Boot toegestaan").equals("ja") || entry.getString("Boot toegestaan").equals("yes")) water.setBootToegestaan(true);
                if(entry.getString("Electrische boot").equals("ja") || entry.getString("Electrische boot").equals("yes")) water.setElectrischeBoot(true);
                if(entry.getString("Roeiboot").equals("ja") || entry.getString("Roeiboot").equals("yes") ) water.setRoeiboot(true);
                if(entry.getString("Voerboot").equals("ja") || entry.getString("Voerboot").equals("yes")) water.setVoerboot(true);
                if(entry.getString("Nachtvissen toegestaan").equals("ja") || entry.getString("Nachtvissen toegestaan").equals("yes")) water.setNachtvissenToegestaan(true);
                if(entry.getString("Vergunning online verkrijgbaar").equals("ja") ||  entry.getString("Vergunning online verkrijgbaar").equals("yes")) water.setVergunningOnlineVerkrijgbaar(true);
                water.setAantalHengels(entry.optInt("Aantal Hengels"));
                water.setCategorie(entry.optInt("Categorie (1 of 2)",2));
                water.setHectare(entry.getString("Hectare"));
                water.setBeschrijving(entry.getString("Omschrijving"));

                dept.addWater(water);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return departments;
    }


    @Override
    protected void onPostExecute(ArrayList<Departement> departmentsList) {
        super.onPostExecute(departmentsList);
        EventBus.getDefault().post(new DepartmentsLoadedEvent(departmentsList));
    }

    public String loadJSONFromAsset() {
        String json = null;
        String fileName = "";
        if(Locale.getDefault().getLanguage().equals("nl")){
            fileName = "data_nl.json";
        } else {
            fileName = "data_en.json";
        }
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }
}
