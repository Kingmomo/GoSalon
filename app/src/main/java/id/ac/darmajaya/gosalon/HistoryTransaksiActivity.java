package id.ac.darmajaya.gosalon;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Adapter.HistoryTransaksiAdapter;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.HistoryTransaksi;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.ResponTransaksi;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.SPreferenced.SPref;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransaksiActivity extends AppCompatActivity {
    private RecyclerView recyclerview;
    private HistoryTransaksiAdapter adapter;
    private List<HistoryTransaksi> historyTransaksi = new ArrayList<>();
    private ResponTransaksi responTransaksi;
    private ProgressDialog pDialog;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_transaksi);


        recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recyclerview.setNestedScrollingEnabled(false);
        mContext = this;

        fetchfrominternet();
        adapter = new HistoryTransaksiAdapter(this, historyTransaksi);
        recyclerview.setAdapter(adapter);


    }

    private void fetchfrominternet() {

        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();

        final Call<ResponTransaksi> datatransaksi = client.getApi().gethistorytransaksi(Prefs.getString(SPref.getId(), null));
        datatransaksi.enqueue(new Callback<ResponTransaksi>() {
            @Override
            public void onResponse(Call<ResponTransaksi> call, Response<ResponTransaksi> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    responTransaksi = response.body();
                    System.out.println("data user " + response.code());
                    historyTransaksi.addAll(responTransaksi.getData());
                    Collections.reverse(historyTransaksi);
                    adapter.notifyDataSetChanged();

                } else {
                    Toasty.error(mContext, "Username dan password salah", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponTransaksi> call, Throwable t) {

            }
        });

    }

}
