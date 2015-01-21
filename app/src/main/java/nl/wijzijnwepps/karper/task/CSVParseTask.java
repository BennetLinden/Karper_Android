package nl.wijzijnwepps.karper.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.event.DepartmentsLoadedEvent;
import nl.wijzijnwepps.karper.model.Departement;

/**
 * Created by Stephan on 14/01/15.
 */
public class CSVParseTask extends AsyncTask<Void,Void,ArrayList<Departement>>{

    private Context context;

    public CSVParseTask(Context context) {
        this.context = context;
    }

    @Override
    protected ArrayList<Departement> doInBackground(Void... voids) {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ";";

        ArrayList<Departement> departmentsList = new ArrayList<Departement>();

        try {

            br = new BufferedReader(new InputStreamReader(context.getAssets().open("Karper9.csv")));
            br.readLine(); //Read first line (headers)
            while ((line = br.readLine()) != null) {

                // use semicolon as separator
                String[] entry = line.split(cvsSplitBy);

                try {
                    int ID = Integer.valueOf(entry[0]);
                    String name = entry[1];

                    Departement departement = new Departement(ID, name);
                    boolean exists = false;
                    for(Departement iteratedDept : departmentsList){
                        if(iteratedDept.getID()==departement.getID()) exists = true;
                    }
                    if(!exists) departmentsList.add(departement);

                } catch (ArrayIndexOutOfBoundsException aioobe){
                    Log.i("error","IndexOutOfBounds reading csv");
                } catch (NumberFormatException nfe){
                    Log.i("error","NumnberFormatException readign csv: "+nfe.getLocalizedMessage());
                }

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return departmentsList;
    }


    @Override
    protected void onPostExecute(ArrayList<Departement> departmentsList) {
        super.onPostExecute(departmentsList);
        EventBus.getDefault().post(new DepartmentsLoadedEvent(departmentsList));
    }
}
