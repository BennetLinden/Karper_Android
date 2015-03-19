package nl.wijzijnwepps.karper.event;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.SearchItem;

/**
 * Created by Stephan on 14/01/15.
 */
public class ItemsFoundEvent {

    private ArrayList<SearchItem> items;

    public ItemsFoundEvent(){
        items = new ArrayList<SearchItem>();
    }

    public ItemsFoundEvent(ArrayList<SearchItem> items) {
        this.items = items;
    }

    public ArrayList<SearchItem> getItems() {
        return items;
    }
}
