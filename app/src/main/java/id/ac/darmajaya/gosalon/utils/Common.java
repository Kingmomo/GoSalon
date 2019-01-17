package id.ac.darmajaya.gosalon.utils;

import java.util.ArrayList;
import java.util.List;

import id.ac.darmajaya.gosalon.Model.User.Auth;

public class Common {

    public void authen (String username, String password){
        Auth auth = new Auth(username, password);
    }


}
