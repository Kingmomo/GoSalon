package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.darmajaya.gosalon.DetailPesanan;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksi;
import id.ac.darmajaya.gosalon.R;

public class HistoryTransaksiAdapter extends RecyclerView.Adapter<HistoryTransaksiAdapter.MyViewHolder> {
    private Context context;
    private List<GetTransaksi> getTransaksi;


    public HistoryTransaksiAdapter(Context context, List<GetTransaksi> getTransaksi) {
        this.context = context;
        this.getTransaksi = getTransaksi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_transaksi, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final GetTransaksi historyTransaksi1 = getTransaksi.get(position);
        String reg = "\\s";
        String[] res = historyTransaksi1.getWaktu_pemesanan().split(reg);
        holder.nama.setText(historyTransaksi1.getNama());
        holder.notelp.setText(historyTransaksi1.getTelp());
        holder.waktu.setText(res[1]);
        holder.tanggal.setText(res[0]);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailPesanan.class);
                intent.putExtra("Produk", historyTransaksi1.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return getTransaksi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView nama, notelp, waktu, tanggal, status;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nama = view.findViewById(R.id.nama);
            notelp = view.findViewById(R.id.notelp);
            waktu = view.findViewById(R.id.waktu);
            tanggal = view.findViewById(R.id.tanggal);
            status = view.findViewById(R.id.status);


        }
    }


}
