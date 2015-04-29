package nl.wijzijnwepps.karper.controller;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import nl.wijzijnwepps.karper.event.ItemsFoundEvent;
import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.SearchItem;
import nl.wijzijnwepps.karper.model.Water;

/**
 * Created by Stephan on 14/01/15.
 */
public class SearchController {

    private Context context;
    private static SearchController instance = null;
    private ArrayList<SearchItem> results;

    public static SearchController getInstance(Context context){
        if(instance==null){
            instance = new SearchController(context);
        }
        return instance;
    }

    private SearchController(Context context){
        this.context = context;
        results = new ArrayList<SearchItem>();
    }

    public void search(String query) {
        results.clear();
        for(Departement dept : DepartementController.getInstance(context).getDepartments()){
            if(dept.getName().toLowerCase().contains(query.toLowerCase())) {
                SearchItem searchItem = new SearchItem();
                searchItem.setType(SearchItem.Type.DEPARTMENT);
                searchItem.setDepartement(dept);
                results.add(searchItem);
            }

            try {
                for(Water water : dept.getWaters()){
                    if(water.getName().toLowerCase().contains(query.toLowerCase()) || water.getCity().toLowerCase().contains(query.toLowerCase())){
                        SearchItem searchItem = new SearchItem();
                        searchItem.setType(SearchItem.Type.WATER);
                        searchItem.setDepartement(dept);
                        searchItem.setWater(water);
                        results.add(searchItem);

                    }
                }
            } catch (NullPointerException npe){
                //Necessary for the loop. The data is not perfect
            }
        }
        EventBus.getDefault().post(new ItemsFoundEvent(results));
    }

    public ArrayList<SearchItem> getResults() {
        return results;
    }
}
