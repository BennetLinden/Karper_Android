package nl.wijzijnwepps.karper.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.fragment.DepartmentFragment;
import nl.wijzijnwepps.karper.fragment.DrawerFragment;
import nl.wijzijnwepps.karper.fragment.HelpFragment;
import nl.wijzijnwepps.karper.fragment.PrivacyFragment;
import nl.wijzijnwepps.karper.fragment.SearchFragment;

public class MainActivity extends Activity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    DrawerFragment drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerFragment = new DrawerFragment();

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new DepartmentFragment())
                .replace(R.id.left_drawer, new DrawerFragment())
                .commit();

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_burger, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(drawerToggle);

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        // If the nav drawer is open, hide action items related to the content view
//        boolean drawerOpen = drawerLayout.isDrawerOpen((View) drawerFragment);
//        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...
        if(item.getItemId()==R.id.action_search){
            //Search
        }

        return super.onOptionsItemSelected(item);
    }

    public void showHomeView(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new DepartmentFragment())
                .commit();
        drawerLayout.closeDrawers();
    }

    public void showSearchView(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new SearchFragment())
                .commit();
        drawerLayout.closeDrawers();
    }

    public void showHelpView(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new HelpFragment())
                .commit();
        drawerLayout.closeDrawers();
    }

    public void showPrivacyView(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, new PrivacyFragment())
                .commit();
        drawerLayout.closeDrawers();
    }


}
