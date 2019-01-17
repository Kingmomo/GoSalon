package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.Model.Toko.DataToko;
import id.ac.darmajaya.gosalon.ProdukActivity;
import id.ac.darmajaya.gosalon.R;
import id.ac.darmajaya.gosalon.TentangActivity;

public class TokoAdapter extends RecyclerView.Adapter<TokoAdapter.MyViewHolder> {
    private Context context;
    private List<DataToko> dataTokos;

    public TokoAdapter(Context context, List<DataToko> dataTokos) {
        this.context = context;
        this.dataTokos = dataTokos;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemview = LayoutInflater.from(context).inflate(R.layout.item_toko, viewGroup, false);
        return new MyViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        DataToko dataToko=  dataTokos.get(position);
        myViewHolder.namatoko.setText(dataToko.getNama_toko());
        if (dataToko.getGambar() != null){
            Glide.with(context).load(dataToko.getGambar()).into(myViewHolder.gambattoko);
        }

    }

    @Override
    public int getItemCount() {
        return dataTokos.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView gambattoko;
        private TextView namatoko;
        private View view;
        private Button btn_pesan, btn_tentangsalon;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            namatoko = (TextView) view.findViewById(R.id.namatoko);
            gambattoko = (ImageView) view.findViewById(R.id.gambatoko);
            btn_pesan = (Button) view.findViewById(R.id.btn_pesan);
            btn_tentangsalon = (Button) view.findViewById(R.id.btn_tentangsalon);

            btn_pesan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ProdukActivity.class);
                    intent.putExtra("ID", dataTokos.get(getAdapterPosition()).getId());
                    btn_pesan.getContext().startActivity(intent);
                    Toasty.success(context, dataTokos.get(getAdapterPosition()).getId(), Toast.LENGTH_LONG).show();
                }
            });

            btn_tentangsalon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, TentangActivity.class);
                    btn_tentangsalon.getContext().startActivity(intent);

                }
            });

        }
    }
}
