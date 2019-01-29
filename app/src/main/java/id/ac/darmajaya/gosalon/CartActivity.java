package id.ac.darmajaya.gosalon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Adapter.CartAdapter;
import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.SPreferenced.MySharedPreference;
import id.ac.darmajaya.gosalon.utils.SimpleDividerItemDecoration;

public class CartActivity extends AppCompatActivity {

    private RecyclerView checkRecyclerView;
    private TextView subTotal;
    private int mSubTotal = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        subTotal = (TextView) findViewById(R.id.sub_total);
        checkRecyclerView = (RecyclerView) findViewById(R.id.checkout_list);



        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CartActivity.this);
        checkRecyclerView.setLayoutManager(linearLayoutManager);
        checkRecyclerView.setHasFixedSize(true);
        checkRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(CartActivity.this));

        // get content of cart
        final MySharedPreference mShared = new MySharedPreference(CartActivity.this);

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();

        DataProduk[] addCartProducts = gson.fromJson(mShared.retrieveProductFromCart(), DataProduk[].class);
        final List<DataProduk> productList = convertObjectArrayToListObject(addCartProducts);

        CartAdapter mAdapter = new CartAdapter(CartActivity.this, productList);
        checkRecyclerView.setAdapter(mAdapter);

        mSubTotal = getTotalPrice(productList);
        Locale localeID = new Locale("in", "ID");
        final NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        subTotal.setText("Harga Total: " + (formatRupiah.format(Integer.parseInt(String.valueOf(mSubTotal)))));


        final Button shoppingButton = (Button)findViewById(R.id.checkout);
        shoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shoppingIntent = new Intent(CartActivity.this, TransaksiActivity.class);
                mShared.addTotalHarga(mSubTotal);
                startActivity(shoppingIntent);
            }
        });

 /*       Button checkButton = (Button)findViewById(R.id.checkout);
        assert checkButton != null;
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent paymentIntent = new Intent(CartActivity.this, PayPalCheckoutActivity.class);
                paymentIntent.putExtra("TOTAL_PRICE", mSubTotal);
                startActivity(paymentIntent);
            }
        });*/


        mAdapter.setOnDataChangeListener(new CartAdapter.OnDataChangeListener() {
            public void onDataChanged(int size) {
                subTotal.setText("Harga Total: " + (formatRupiah.format(Integer.parseInt(String.valueOf(size)))));

            }

            @Override
            public void onDataVerify(Boolean cek) {
                System.out.println("perubahan data "+ cek);
                shoppingButton.setClickable(!cek);
                if (!cek == false)
                Toasty.error(CartActivity.this, "Jasa tidak boleh beda toko dalam 1 transaksi", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private List<DataProduk> convertObjectArrayToListObject(DataProduk[] allProducts) {
        List<DataProduk> mProduct = new ArrayList<DataProduk>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    private int returnQuantityByProductName(String productName, List<DataProduk> mProducts) {
        int quantityCount = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            DataProduk pObject = mProducts.get(i);
            if (pObject.getNama_produk().trim().equals(productName.trim())) {
                quantityCount++;
            }
        }
        return quantityCount;
    }

    private int getTotalPrice(List<DataProduk> mProducts) {
        int totalCost = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            DataProduk pObject = mProducts.get(i);
            totalCost = totalCost + Integer.parseInt(pObject.getHarga_produk());
        }
        return totalCost;
    }




}
