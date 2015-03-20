package nl.wijzijnwepps.karper.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

import nl.wijzijnwepps.karper.R;
import nl.wijzijnwepps.karper.widget.KarperDialog;


public class ForgotPasswordFragment extends Fragment implements RequestPasswordResetCallback {

    private EditText emailField;
    private RelativeLayout overlay;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_password, container, false);

        emailField = (EditText) view.findViewById(R.id.emailField);

        overlay = (RelativeLayout) view.findViewById(R.id.overlay);
        overlay.setVisibility(View.GONE);

        Button loginButton = (Button) view.findViewById(R.id.buttonLogin);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                overlay.setVisibility(View.VISIBLE);
                ParseUser.requestPasswordResetInBackground(emailField.getText().toString(),ForgotPasswordFragment.this);
            }
        });
        return view;
    }

    @Override
    public void done(ParseException e) {
        overlay.setVisibility(View.GONE);

        if (e == null) {
            // An email was successfully sent with reset
            // instructions.
           new KarperDialog(getActivity(),getActivity().getString(R.string.success),getActivity().getString(R.string.email_sent));
        } else {
            // Something went wrong. Look at the ParseException
            // to see what's up.
            new KarperDialog(getActivity(),getActivity().getString(R.string.fail),getActivity().getString(R.string.email_not_sent));
        }
    }
}
