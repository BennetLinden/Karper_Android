package nl.wijzijnwepps.karper.helper;

import android.content.Context;

/**
 * Created by Stephan on 25-11-14.
 */
public class SecurePreferencesHelper {

    private Context context;
    private SecurePreferences prefs;

    public SecurePreferencesHelper(Context context){
        this.context = context;
        prefs = new SecurePreferences(context,"karper-prefs","2Lm5OFYOKC8LWLVk61uRX95M9NF1jTnd",true);
    }

    public void putBoolean(String key, boolean value) {
        prefs.put(key,""+value);
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        if(prefs.containsKey(key)){
            String result = prefs.getString(key);
            if(result.equals("true")) return true;
            else return false;
        }
        else return defaultValue;
    }

    public void putString(String key, String value) {
        prefs.put(key,value);
    }

    public String getString(String key, String defaultValue) {
        if(prefs.containsKey(key)){
            return prefs.getString(key);
        }
        else return defaultValue;
    }


}
