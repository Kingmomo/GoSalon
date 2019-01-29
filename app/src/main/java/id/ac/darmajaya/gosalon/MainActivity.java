package id.ac.darmajaya.gosalon;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Adapter.TokoAdapter;
import id.ac.darmajaya.gosalon.Model.Toko.DataToko;
import id.ac.darmajaya.gosalon.Model.Toko.ResponseToko;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.SPreferenced.SPref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerview;
    private TokoAdapter adapter;
    private List<DataToko> dataTokos = new ArrayList<>();
    private ResponseToko responseToko;
    private ProgressDialog pDialog;
    private Context mContext;
    private TextView name;
    private Dialog MyDialog;
    private Button close;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setNestedScrollingEnabled(false);
        mContext = this;
        adapter = new TokoAdapter(this, dataTokos);

        fetchfrominternet();
        recyclerview.setAdapter(adapter);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        name = (TextView) headerView.findViewById(R.id.name);
        name.setText(Prefs.getString(SPref.getNama(), null));
    }

    private void fetchfrominternet() {

        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        Call<ResponseToko> user = client.getApi().getdatatoko();
        user.enqueue(new Callback<ResponseToko>() {
            @Override
            public void onResponse(Call<ResponseToko> call, Response<ResponseToko> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    responseToko = response.body();
                    dataTokos.addAll(responseToko.getData());
                    adapter.notifyDataSetChanged();

                } else {
                    Toasty.error(mContext, "Username dan password salah", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseToko> call, Throwable t) {
                pDialog.hide();
                Toasty.error(mContext, "Koneksi Tidak ada", Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_histori) {
            startActivity(new Intent(this, HistoryTransaksiActivity.class));
        } else if (id == R.id.nav_logout) {
            dialog();
        } else if (id == R.id.tentang_app) {
            MyCustomAlertDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Anda ingin Keluar")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Prefs.clear();
                        finish();
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void MyCustomAlertDialog() {
        MyDialog = new Dialog(MainActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.tentang_aplikasi);
        MyDialog.setTitle("My Custom Dialog");

        close = (Button) MyDialog.findViewById(R.id.close);
        close.setEnabled(true);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDialog.cancel();
            }
        });
        MyDialog.show();
    }
}
