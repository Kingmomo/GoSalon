package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.HistoryTransaksi;
import id.ac.darmajaya.gosalon.R;

public class HistoryTransaksiAdapter extends RecyclerView.Adapter<HistoryTransaksiAdapter.MyViewHolder> {
    private Context context;
    private List<HistoryTransaksi> historyTransaksi;

    public HistoryTransaksiAdapter(Context context, List<HistoryTransaksi> historyTransaksi) {
        this.context = context;
        this.historyTransaksi = historyTransaksi;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_history_transaksi, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HistoryTransaksi historyTransaksi1 = historyTransaksi.get(position);
        holder.nama.setText(historyTransaksi1.getNama());
        holder.notelp.setText(historyTransaksi1.getTelp());
        holder.waktu.setText(historyTransaksi1.getWaktu_pemesanan());

    }

    @Override
    public int getItemCount() {
        return historyTransaksi.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView nama, notelp, waktu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nama = view.findViewById(R.id.nama);
            notelp = view.findViewById(R.id.notelp);
            waktu = view.findViewById(R.id.waktu);


        }
    }
}
