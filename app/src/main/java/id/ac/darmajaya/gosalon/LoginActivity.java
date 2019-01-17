package id.ac.darmajaya.gosalon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Model.User.DataUser;
import id.ac.darmajaya.gosalon.Model.User.ResponseLogin;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.SPreferenced.SPref;
import id.ac.darmajaya.gosalon.utils.Validate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText email, password;
    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private ProgressDialog pDialog;
    private Context mContext;
    private ResponseLogin dataUsers;
    private List<DataUser> listUsers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);

        email = (TextInputEditText) findViewById(R.id.email);
        password = (TextInputEditText) findViewById(R.id.password);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mContext = this;


        if (Prefs.getInt(SPref.getUserstatus(), 0) == 1) {
            Toasty.success(mContext, "Selamat Datang " + Prefs.getString(SPref.getNama(), null), Toast.LENGTH_LONG).show();
            startActivity(new Intent(mContext, MainActivity.class));
            finish();
        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate_login())
                    login();
            }
        });

        ((TextView) findViewById(R.id.signup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistationActivity.class));
            }
        });

    }

    public boolean validate_login() {
        return (!Validate.cek(email) && !Validate.cek(password)) ? true : false;
    }

    public void login() {
        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        Call<ResponseLogin> user = client.getApi().getresponauth(email.getText().toString().trim(), password.getText().toString().trim());
        user.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    dataUsers = response.body();
                    listUsers.addAll(dataUsers.getData());
                    setPreference(listUsers.get(0));
                    Toasty.success(mContext, "Selamat Datang " + listUsers.get(0).getNama(), Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } else {
                    Toasty.error(mContext, "Username dan password salah", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                pDialog.hide();
                Toasty.error(mContext, "Koneksi Tidak ada", Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }

    private void setPreference(DataUser user) {
        Prefs.putString(SPref.getId(), user.getId());
        Prefs.putString(SPref.getPassword(), user.getPassword());
        Prefs.putString(SPref.getNama(), user.getNama());
        Prefs.putString(SPref.getAlamat(), user.getAlamat());
        Prefs.putString(SPref.getEmail(), user.getEmail());
        Prefs.putString(SPref.getTelp(), user.getTelp());
        Prefs.putInt(SPref.getUserstatus(), 1);
    }


}
