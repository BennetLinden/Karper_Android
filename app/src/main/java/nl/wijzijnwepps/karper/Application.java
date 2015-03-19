package nl.wijzijnwepps.karper;

import com.parse.Parse;

/**
 * Created by Stephan on 18/03/15.
 */
public class Application extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "j9T1TK0GdHtFN0vEjxOIf3fG2ZAMA6RFfvXuqwV0", "xUpMGTk6nhLnJ7Io2fCidsDQdbhzplQ91KgQ1UUI");
    }
}
