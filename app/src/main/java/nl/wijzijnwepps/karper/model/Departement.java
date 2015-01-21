package nl.wijzijnwepps.karper.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Stephan on 14/01/15.
 */
public class Departement implements Serializable{

    private int ID;
    private String name;
    private ArrayList<Water> waters;

    public Departement(int ID, String name) {
        this.ID = ID;
        this.name = name;
        waters = new ArrayList<Water>();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Water> getWaters() {
        return waters;
    }

    public void setWaters(ArrayList<Water> waters) {
        this.waters = waters;
    }

    public void addWater(Water water){
        waters.add(water);
    }
}
