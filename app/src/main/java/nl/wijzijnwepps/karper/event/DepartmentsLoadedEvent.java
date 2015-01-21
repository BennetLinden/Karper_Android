package nl.wijzijnwepps.karper.event;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.model.Departement;

/**
 * Created by Stephan on 14/01/15.
 */
public class DepartmentsLoadedEvent {

    private ArrayList<Departement> departments;

    public DepartmentsLoadedEvent(){
        departments = new ArrayList<Departement>();
    }

    public DepartmentsLoadedEvent(ArrayList<Departement> departments) {
        this.departments = departments;
    }

    public ArrayList<Departement> getDepartments() {
        return departments;
    }
}
