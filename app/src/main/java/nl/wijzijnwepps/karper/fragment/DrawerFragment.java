package nl.wijzijnwepps.karper.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nl.wijzijnwepps.karper.R;

/**
 * Created by Stephan on 14/02/15.
 */
public class DrawerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container,
                false);

        return rootView;
    }
}
