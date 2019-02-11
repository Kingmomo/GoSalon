package id.ac.darmajaya.gosalon;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import id.ac.darmajaya.gosalon.Model.Toko.DataToko;

public class TentangSalonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tentang_salon);
        TextView namasalon = findViewById(R.id.namasalon);
        TextView pemilik = findViewById(R.id.pemilik);
        TextView alamat = findViewById(R.id.alamat);
        ImageView gambartoko = findViewById(R.id.gambartoko);


        Glide.with(this)
                .load("http://" + getIntent().getStringExtra("foto"))
                .apply(
                        new RequestOptions()
                                .placeholder(R.drawable.item_toko)
                                .fitCenter())
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gambartoko);
        namasalon.setText("Pemilik: "+getIntent().getStringExtra("nama"));
        pemilik.setText(getIntent().getStringExtra("pemilik"));
        alamat.setText(getIntent().getStringExtra("alamat"));
    }
}
