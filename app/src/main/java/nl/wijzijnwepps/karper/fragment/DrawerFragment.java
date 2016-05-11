package nl.wijzijnwepps.karper.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseUser;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.activity.LoginActivity;
import nl.wijzijnwepps.karper.activity.MainActivity;
import nl.wijzijnwepps.karper.helper.SecurePreferencesHelper;

/**
 * Created by Stephan on 14/02/15.
 */
public class DrawerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_drawer, container,
                false);

        TextView buttonHome = (TextView) rootView.findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showHomeView();
            }
        });

        TextView buttonSearch = (TextView) rootView.findViewById(R.id.button_search);
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showSearchView();
            }
        });

        TextView buttonHelp = (TextView) rootView.findViewById(R.id.button_help);
        buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showHelpView();
            }
        });

        TextView buttonPrivacy = (TextView) rootView.findViewById(R.id.button_privacy);
        buttonPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)getActivity()).showPrivacyView();
            }
        });

        TextView buttonLogout = (TextView) rootView.findViewById(R.id.button_logout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logOut();
                SecurePreferencesHelper helper = new SecurePreferencesHelper(getActivity());
                helper.putBoolean("autoLogin", false);
                Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                startActivity(loginIntent);
                getActivity().finish();
            }
        });

        TextView linkWepps = (TextView) rootView.findViewById(R.id.link_wepps);
        linkWepps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wijzijnwepps.nl"));
                startActivity(browserIntent);
            }
        });

        return rootView;
    }
}
