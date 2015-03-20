package nl.wijzijnwepps.karper.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import nl.wijzijnwepps.karper.R;

/**
 * Created by Stephan on 19/03/15.
 */
public class PrivacyFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);

        WebView privacyWebView = (WebView) view.findViewById(R.id.mainWebView);
        privacyWebView.setBackgroundColor(0x00000000);//Make transparent so the beige background set in XML is visible
        privacyWebView.loadUrl("file:///android_asset/Privacybeleid.htm");
        return view;
    }
}
