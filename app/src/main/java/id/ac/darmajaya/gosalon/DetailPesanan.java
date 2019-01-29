package id.ac.darmajaya.gosalon;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import id.ac.darmajaya.gosalon.Adapter.DetailPesananAdapter;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksi;
import id.ac.darmajaya.gosalon.utils.Common;
import id.ac.darmajaya.gosalon.utils.SimpleDividerItemDecoration;

public class DetailPesanan extends Activity {
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pesanan);
        recyclerView = findViewById(R.id.recyclerview);


        GetTransaksi detailproduk = Common.findid(getIntent().getStringExtra("Produk"));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));

        setDetailPokemon(detailproduk);


    }

    private void setDetailPokemon(GetTransaksi getTransaksi) {

        DetailPesananAdapter adapter = new DetailPesananAdapter(this, getTransaksi.getProduk());
        recyclerView.setAdapter(adapter);


    }
}
