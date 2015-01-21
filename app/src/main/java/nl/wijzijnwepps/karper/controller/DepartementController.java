package nl.wijzijnwepps.karper.controller;

import android.content.Context;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.model.Departement;

/**
 * Created by Stephan on 14/01/15.
 */
public class DepartementController {

    private ArrayList<Departement> departments;
    private Context context;
    private static DepartementController instance = null;

    public static DepartementController getInstance(Context context){
        if(instance==null){
            instance = new DepartementController(context);
        }
        return instance;
    }

    private DepartementController(Context context){
        this.context = context;
        departments = new ArrayList<Departement>();
    }

    public ArrayList<Departement> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<Departement> departments) {
        this.departments = departments;
    }
}
