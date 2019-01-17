package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.R;
import id.ac.darmajaya.gosalon.TransaksiActivity;

import static android.content.Context.MODE_PRIVATE;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.MyViewHolder> {

    private Context context;
    private List<DataProduk> dataProduks;

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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
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



        Glide.with(context).load(dataProduk.getFoto_produk()).apply(options).into(myViewHolder.gambar);

        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TransaksiActivity.class);
                setSharedPreference(dataProduk.getId_toko(), dataProduk.getId(), dataProduk.getNama_produk(), dataProduk.getHarga_produk());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataProduks.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
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

    public void setSharedPreference(String idtoko, String idproduk, String namaproduk, String harga){
        SharedPreferences pref = context.getSharedPreferences("TransaksiSalon", MODE_PRIVATE);
        SharedPreferences.Editor spref = pref.edit();
        spref.clear();
        spref.putString("idtoko", idtoko);
        spref.putString("idproduk", idproduk);
        spref.putString("namaproduk", namaproduk);
        spref.putString("harga", harga);
        spref.commit();
    }

}
