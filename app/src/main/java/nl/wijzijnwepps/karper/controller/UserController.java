package nl.wijzijnwepps.karper.controller;

import android.content.Context;

import java.util.ArrayList;

import nl.wijzijnwepps.karper.model.Departement;
import nl.wijzijnwepps.karper.model.User;

/**
 * Created by Stephan on 14/01/15.
 */
public class UserController {

    private User user;

    private Context context;
    private static UserController instance = null;

    public synchronized static UserController getInstance(Context context){
        if(instance==null){
            instance = new UserController(context);
        }
        return instance;
    }

    private UserController(Context context){
        this.context = context;
        this.user = new User();
    }
}
