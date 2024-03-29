package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.ProdukActivity;
import id.ac.darmajaya.gosalon.R;
import id.ac.darmajaya.gosalon.SPreferenced.MySharedPreference;

import static android.content.Context.MODE_PRIVATE;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder> {

    private Context context;
    private List<DataProduk> dataProduks;
    private MySharedPreference sharedPreference;
    private Gson gson;
    private int cartProductNumber = 0;


    public ProdukAdapter(Context context, List<DataProduk> dataProduks) {
        this.context = context;
        this.dataProduks = dataProduks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_barang, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
        final DataProduk dataProduk = dataProduks.get(position);
        myViewHolder.namaproduk.setText(dataProduk.getNama_produk());
        myViewHolder.waktupengerjaan.setText(dataProduk.getWaktu_pengerjaan());
        final long harga = Long.parseLong(dataProduk.getHarga_produk());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        myViewHolder.hargaproduk.setText(formatRupiah.format(Double.parseDouble(String.valueOf(harga))));
//        Glide.with(context).load(dataProduk.getFoto_produk()).into(myViewHolder.gambar);
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round);



        Glide.with(context)
                .load("http://"+dataProduk.getFoto_produk())
                .apply(
                        new RequestOptions()
                                .placeholder(R.color.pink_200)
                                .fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(myViewHolder.gambar);


        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
 /*
                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();

                String stringObjectRepresentation = gson.toJson(singleProduct);

                Intent intent = new Intent(context, CartActivity.class);
                intent.putExtra("PRODUCT", stringObjectRepresentation);
                setSharedPreference(dataProduk.getId_toko(), dataProduk.getId(), dataProduk.getNama_produk(), dataProduk.getHarga_produk());
                context.startActivity(intent);*/


                sharedPreference = new MySharedPreference(context);
                GsonBuilder builder = new GsonBuilder();
                gson = builder.create();

                String singleProductt = gson.toJson(dataProduk);
                final DataProduk singleProduct = gson.fromJson(singleProductt, DataProduk.class);

                //increase product count
                String productsFromCart = sharedPreference.retrieveProductFromCart();
                if (productsFromCart.equals("")) {
                    List<DataProduk> cartProductList = new ArrayList<DataProduk>();
                    cartProductList.add(singleProduct);
                    String cartValue = gson.toJson(cartProductList);
                    sharedPreference.addProductToTheCart(cartValue);
                    cartProductNumber = cartProductList.size();
                } else {
                    String productsInCart = sharedPreference.retrieveProductFromCart();
                    DataProduk[] storedProducts = gson.fromJson(productsInCart, DataProduk[].class);

                    List<DataProduk> allNewProduct = convertObjectArrayToListObject(storedProducts);
                    allNewProduct.add(singleProduct);
                    String addAndStoreNewProduct = gson.toJson(allNewProduct);
                    sharedPreference.addProductToTheCart(addAndStoreNewProduct);
                    cartProductNumber = allNewProduct.size();
                }
                sharedPreference.addProductCount(cartProductNumber);
                invalidateCart();

            }
        });

    }

    @Override
    public int getItemCount() {
        return dataProduks.size();
    }


    private List<DataProduk> convertObjectArrayToListObject(DataProduk[] allProducts) {
        List<DataProduk> mProduct = new ArrayList<DataProduk>();
        Collections.addAll(mProduct, allProducts);
        return mProduct;
    }

    private void invalidateCart() {
        ((ProdukActivity) context).invalidateOptionsMenu();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private ImageView gambar;
        private TextView namaproduk, hargaproduk, waktupengerjaan;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            gambar = (ImageView) view.findViewById(R.id.gambar);
            namaproduk = (TextView) view.findViewById(R.id.namaproduk);
            hargaproduk = (TextView) view.findViewById(R.id.harga);
            waktupengerjaan = (TextView) view.findViewById(R.id.waktupengerjaan);
/*
            int[] androidColors = context.getResources().getIntArray(R.array.mdcolor_random);
            int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
            cardView.setCardBackgroundColor(randomAndroidColor);*/


        }
    }

}
