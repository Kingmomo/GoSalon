package id.ac.darmajaya.gosalon.SPreferenced;


import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefToko{

    private SharedPreferences prefs;

    private Context context;

    public SharedPrefToko(Context context){
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_TOKO, Context.MODE_PRIVATE);
    }

    public void SetNamToko(String product){
        SharedPreferences.Editor edits = prefs.edit();
        edits.putString(Constants.NAMA_TOKO, product);
        edits.apply();
    }

    public String GetAddNamToko(){
        return prefs.getString(Constants.NAMA_TOKO, "");
    }


    public void deletedata(){
        SharedPreferences.Editor edits = prefs.edit();
        edits.clear().commit();
    }

}

