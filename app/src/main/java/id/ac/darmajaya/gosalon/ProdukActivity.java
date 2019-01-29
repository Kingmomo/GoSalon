package id.ac.darmajaya.gosalon;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Adapter.ProdukAdapter;
import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.Model.Produk.ResponseProduk;
import id.ac.darmajaya.gosalon.Retrofit.client;
import id.ac.darmajaya.gosalon.SPreferenced.MySharedPreference;
import id.ac.darmajaya.gosalon.utils.SpacesItemDecoration;
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
    private MySharedPreference sharedPreference;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produk);
        mContext = this;
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);

        sharedPreference = new MySharedPreference(this);

      /*  GridLayoutManager  gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanCount(2);
        recyclerview.setLayoutManager(gridLayoutManager);
*/
        fetchfrominternet();

        //recyclerview
        recyclerview.setNestedScrollingEnabled(false);
        GridLayoutManager mGrid = new GridLayoutManager(this, 2);
        recyclerview.setLayoutManager(mGrid);
        recyclerview.setHasFixedSize(true);
        recyclerview.addItemDecoration(new SpacesItemDecoration(2, 12, false));
        adapter = new ProdukAdapter(mContext, dataProduks);
        recyclerview.setAdapter(adapter);


    }

    private void fetchfrominternet() {

        pDialog = new ProgressDialog(this);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();


        Call<ResponseProduk> user = client.getApi().getproduktoko(getIntent().getStringExtra("ID"));
        user.enqueue(new Callback<ResponseProduk>() {
            @Override
            public void onResponse(Call<ResponseProduk> call, Response<ResponseProduk> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    responseProduk = response.body();
                    dataProduks.addAll(responseProduk.getData());
                    adapter.notifyDataSetChanged();

                } else {
                    Toasty.error(mContext, "Username dan password salah", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }

            }

            @Override
            public void onFailure(Call<ResponseProduk> call, Throwable t) {
                pDialog.hide();
                Toasty.error(mContext, "Koneksi Tidak ada", Toast.LENGTH_LONG).show();
                if (pDialog.isShowing())
                    pDialog.dismiss();
                System.out.println("data user" + t.getMessage());

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_shop);
        int mCount = sharedPreference.retrieveProductCount();
        menuItem.setIcon(buildCounterDrawable(mCount, R.drawable.ic_cart));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_shop) {
            Intent checkoutIntent = new Intent(ProdukActivity.this, CartActivity.class);
            startActivity(checkoutIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Drawable buildCounterDrawable(int count, int backgroundImageId) {
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.shopping_layout, null);
        view.setBackgroundResource(backgroundImageId);

        if (count == 0) {
            View counterTextPanel = view.findViewById(R.id.counterValuePanel);
            counterTextPanel.setVisibility(View.GONE);
        } else {
            TextView textView = (TextView) view.findViewById(R.id.count);
            textView.setText("" + count);
        }

        view.measure(
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.setDrawingCacheEnabled(true);
        view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);

        return new BitmapDrawable(getResources(), bitmap);
    }

    private void invalidateCart() {
        invalidateOptionsMenu();
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateCart();

    }


}
