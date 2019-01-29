package id.ac.darmajaya.gosalon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

import id.ac.darmajaya.gosalon.Adapter.DetailPesananAdapter;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksi;
import id.ac.darmajaya.gosalon.utils.Common;
import id.ac.darmajaya.gosalon.utils.SimpleDividerItemDecoration;

public class DetailPesanan extends Activity {
    private RecyclerView recyclerView;
    private TextView subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        recyclerView = findViewById(R.id.recyclerview);
        subtotal = findViewById(R.id.totalharga);


        GetTransaksi detailproduk = Common.findid(getIntent().getStringExtra("Produk"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        setDetailPokemon(detailproduk);


    }

    private void setDetailPokemon(GetTransaksi getTransaksi) {

        DetailPesananAdapter adapter = new DetailPesananAdapter(this, getTransaksi.getProduk());
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        adapter.setTotalhargaChanged(new DetailPesananAdapter.OnDataChangeListener() {
            @Override
            public void onDataChanged(int totalharga) {
                subtotal.setText("Harga Total: " + (formatRupiah.format(Integer.parseInt(String.valueOf(totalharga)))));
            }
        });

        recyclerView.setAdapter(adapter);


    }
}
