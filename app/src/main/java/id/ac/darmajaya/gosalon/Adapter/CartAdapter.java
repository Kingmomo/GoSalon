package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.R;
import id.ac.darmajaya.gosalon.SPreferenced.MySharedPreference;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {

    Context context;
    List<DataProduk> dataProduks;
    private OnDataChangeListener mOnDataChangeListener;
    private MySharedPreference sharedPreference;
    private int cartProductNumber = 0;

    public CartAdapter(Context context, List<DataProduk> dataProduks) {
        this.context = context;
        this.dataProduks = dataProduks;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final DataProduk dataProduk = dataProduks.get(position);

        holder.namaproduk.setText(dataProduk.getNama_produk());

        final long harga = Long.parseLong(dataProduk.getHarga_produk());
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        holder.hargaproduk.setText(formatRupiah.format(Double.parseDouble(String.valueOf(harga))));
        holder.quantity.setText("1");
        holder.namatoko.setText(getnamatoko(Integer.parseInt(dataProduk.getId_toko())));

        holder.removeProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataProduks.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, dataProduks.size());


                GsonBuilder builder = new GsonBuilder();
                Gson gson = builder.create();
                sharedPreference = new MySharedPreference(context);

                DataProduk[] addCartProducts = gson.fromJson(sharedPreference.retrieveProductFromCart(), DataProduk[].class);
                List<DataProduk> allNewProduct = convertObjectArrayToListObject(addCartProducts);

                allNewProduct.remove(position);
                String addAndStoreNewProduct = gson.toJson(allNewProduct);
                sharedPreference.addProductToTheCart(addAndStoreNewProduct);
                cartProductNumber = allNewProduct.size();
                System.out.println("data produk " + allNewProduct.size());
                sharedPreference.addProductCount(cartProductNumber);

                doButtonOneClickActions(allNewProduct);
                System.out.println("data produk " + getverifyjasa(dataProduks));

            }
        });
        doButtonOneClickActions(dataProduks);



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

    public interface OnDataChangeListener {
        public void onDataChanged(int size);
        public void onDataVerify(Boolean cek);
    }

    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    private void doButtonOneClickActions(List<DataProduk> mProducts) {
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChanged(getTotalPrice(mProducts));
            mOnDataChangeListener.onDataVerify(getverifyjasa(mProducts));
        }
    }

    private int getTotalPrice(List<DataProduk> mProducts) {
        int totalCost = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            DataProduk pObject = mProducts.get(i);
            totalCost = totalCost + Integer.parseInt(pObject.getHarga_produk());
        }
        return totalCost;

    }


    private String getnamatoko(int idtoko) {
        String namatoko;
        switch (idtoko) {
            case 1:
                namatoko = "Salon Intan";
                break;
            case 2:
                namatoko = "Salon Arie";
                break;
            case 3:
                namatoko = "Salon Endi";
                break;
            case 4:
                namatoko = "Salon Vstyle";
                break;
            case 5:
                namatoko = "Salon Belezza";
                break;
            default:
                namatoko = "Invalid";
                break;
        }
        return namatoko;
    }


    private Boolean getverifyjasa(List<DataProduk> mProducts) {
        Boolean verifiy = false;

        for (int i = 0; i < mProducts.size(); i++) {
            for (int j = i + 1; j < mProducts.size(); j++) {
                if (!mProducts.get(i).getId_toko().equals(mProducts.get(j).getId_toko())) {
                    verifiy = true;
                }
            }
        }
        return verifiy;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView quantity, namaproduk, hargaproduk, removeProduct, namatoko;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            namaproduk = (TextView) itemView.findViewById(R.id.namaproduk);
            hargaproduk = (TextView) itemView.findViewById(R.id.hargaproduk);
            removeProduct = (TextView) itemView.findViewById(R.id.remove_from_cart);
            namatoko = (TextView) itemView.findViewById(R.id.namatoko);
        }
    }
}
