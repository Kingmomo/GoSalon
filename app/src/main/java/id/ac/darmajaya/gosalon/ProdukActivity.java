package id.ac.darmajaya.gosalon;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Adapter.ProdukAdapter;
import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.Model.Produk.ResponseProduk;
import id.ac.darmajaya.gosalon.Retrofit.client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProdukActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private ResponseProduk responseProduk;
    private List<DataProduk> dataProduks = new ArrayList<>();
    private Context mContext;
    private ProdukAdapter adapter;
    private RecyclerView recyclerview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        mContext = this;
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerview.setHasFixedSize(true);
        recyclerview.setNestedScrollingEnabled(false);

      /*  GridLayoutManager  gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanCount(2);
        recyclerview.setLayoutManager(gridLayoutManager);
*/
        recyclerview.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        adapter = new ProdukAdapter(mContext, dataProduks);

        fetchfrominternet();
        recyclerview.setAdapter(adapter);




    }

    private void fetchfrominternet() {

        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        Call<ResponseProduk> user=client.getApi().getproduktoko(getIntent().getStringExtra("ID"));
        user.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                pDialog.hide();
                if (response.isSuccessful()){
                    responseProduk=response.body();
                    dataProduks.addAll(responseProduk.getData());
                    adapter.notifyDataSetChanged();

                }else{
                    Toasty.error(mContext,"Username dan password salah",Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                pDialog.hide();
                Toasty.error(mContext,"Koneksi Tidak ada",Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }

}
