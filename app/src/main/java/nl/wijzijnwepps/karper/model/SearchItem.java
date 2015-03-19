package nl.wijzijnwepps.karper.model;

/**
 * Created by Stephan on 19/03/15.
 */
public class SearchItem {

    public enum Type {WATER,DEPARTMENT}

    private Type type;
    private Departement departement;
    private Water water;

    public Departement getDepartement() {
        return departement;
    }

    public void setDepartement(Departement departement) {
        this.departement = departement;
    }

    public Water getWater() {
        return water;
    }

    public void setWater(Water water) {
        this.water = water;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
