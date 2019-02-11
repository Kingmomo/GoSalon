package id.ac.darmajaya.gosalon.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import id.ac.darmajaya.gosalon.DetailPesanan;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksiProdukList;
import id.ac.darmajaya.gosalon.Model.Produk.DataProduk;
import id.ac.darmajaya.gosalon.R;

public class DetailPesananAdapter extends RecyclerView.Adapter<DetailPesananAdapter.MyViewHolder> {
    private OnDataChangeListener mOnDataChangeListener;
    private Context context;
    private List<GetTransaksiProdukList> getTransaksiProdukListList;


    public DetailPesananAdapter(Context context, List<GetTransaksiProdukList> getTransaksiProdukListList) {
        this.context = context;
        this.getTransaksiProdukListList = getTransaksiProdukListList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_detail_pesanan, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        GetTransaksiProdukList getTransaksiProdukList = getTransaksiProdukListList.get(position);
        holder.namaproduk.setText(getTransaksiProdukList.getNama_produk());
        holder.namatoko.setText(getTransaksiProdukList.getWaktu_pengerjaan());
        holder.hargaproduk.setText(getTransaksiProdukList.getHarga_produk());

        doButtonOneClickActions(getTransaksiProdukListList);

    }

    @Override
    public int getItemCount() {
        return getTransaksiProdukListList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private View view;
        private TextView namaproduk, namatoko, hargaproduk;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            namaproduk = view.findViewById(R.id.namaproduk);
            namatoko = view.findViewById(R.id.namatoko);
            hargaproduk = view.findViewById(R.id.hargaproduk);


        }
    }

    public interface OnDataChangeListener {
        public void onDataChanged(int totalharga);
    }


    public void setTotalhargaChanged(OnDataChangeListener onDataChangeListener) {
        mOnDataChangeListener = onDataChangeListener;
    }

    private void doButtonOneClickActions(List<GetTransaksiProdukList> mProducts) {
        if (mOnDataChangeListener != null) {
            mOnDataChangeListener.onDataChanged(getTotalPrice(mProducts));
        }
    }

    private int getTotalPrice(List<GetTransaksiProdukList> mProducts) {
        int totalCost = 0;
        for (int i = 0; i < mProducts.size(); i++) {
            GetTransaksiProdukList pObject = mProducts.get(i);
            totalCost = totalCost + Integer.parseInt(pObject.getHarga_produk());
        }
        return totalCost;

    }

    private String getstatus(int status) {
        String statuspesanan;
        switch (status) {
            case 0:
                statuspesanan = "Pesanan Belum Diproses";
                break;
            case 1:
                statuspesanan = "Pesanan Sudah Diproses";
                break;
            case 2:
                statuspesanan = "Pesanan Sedang Dikerjakan oleh Karyawaan";
                break;
            case 3:
                statuspesanan = "Pesanan Selesai";
                break;
            default:
                statuspesanan = "Invalid";
                break;

        }
        return statuspesanan;
    }
}
