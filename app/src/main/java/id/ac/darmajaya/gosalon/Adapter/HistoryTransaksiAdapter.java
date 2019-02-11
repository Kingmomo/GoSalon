package id.ac.darmajaya.gosalon.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import es.dmoral.toasty.Toasty;
import id.ac.darmajaya.gosalon.DetailPesanan;
import id.ac.darmajaya.gosalon.Model.HistoryTransaksi.GetTransaksi;
import id.ac.darmajaya.gosalon.Model.Konfirmasi.KonfrimasiSelesai;
import id.ac.darmajaya.gosalon.Model.Konfirmasi.ResponseSelesai;
import id.ac.darmajaya.gosalon.R;
import id.ac.darmajaya.gosalon.Retrofit.client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryTransaksiAdapter extends RecyclerView.Adapter<HistoryTransaksiAdapter.MyViewHolder> {
    private Context context;
    private List<GetTransaksi> getTransaksi;
    private ProgressBar progress_bar;
    private ProgressDialog pDialog;


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
        holder.status.setText(getstatus(Integer.parseInt(historyTransaksi1.getStatus())));
        holder.sort.setText("Pesanan No " + historyTransaksi1.getId());

        if (historyTransaksi1.getStatus().equals("0")) {
            holder.btn_konfirmasi.setEnabled(false);
            holder.btn_konfirmasi.setBackgroundColor(context.getResources().getColor(R.color.grey_40));
            holder.btn_konfirmasi.setText("Belum Dikonfirmasi Admin");
        } else if (historyTransaksi1.getStatus().equals("1")) {
            holder.btn_konfirmasi.setEnabled(false);
            holder.btn_konfirmasi.setText("Sudah Dikonfirmasi Karyawan");
            holder.btn_konfirmasi.setBackgroundColor(context.getResources().getColor(R.color.yellow_400));
        } else if (historyTransaksi1.getStatus().equals("2")) {
            holder.btn_konfirmasi.setEnabled(true);
            holder.btn_konfirmasi.setText("Konfirmasi Transaksi");
            holder.btn_konfirmasi.setBackgroundColor(context.getResources().getColor(R.color.pink_300));
        } else if (historyTransaksi1.getStatus().equals("3")) {
            holder.btn_konfirmasi.setEnabled(false);
            holder.btn_konfirmasi.setText("Transaksi Selesai");
            holder.btn_konfirmasi.setBackgroundColor(context.getResources().getColor(R.color.green_500));
        } else {
            holder.btn_konfirmasi.setEnabled(true);

        }
        holder.btn_konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogkonfirmasi(historyTransaksi1);
            }
        });


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
        private TextView nama, notelp, waktu, tanggal, status, sort;
        private Button btn_konfirmasi;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            nama = view.findViewById(R.id.nama);
            notelp = view.findViewById(R.id.notelp);
            waktu = view.findViewById(R.id.waktu);
            tanggal = view.findViewById(R.id.tanggal);
            status = view.findViewById(R.id.status);
            sort = view.findViewById(R.id.sort);
            btn_konfirmasi = view.findViewById(R.id.btn_konfirmasi);

        }
    }

    private void dialogkonfirmasi(final GetTransaksi getTransaksi) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Konfirmasi Pesanan")
                .setPositiveButton("Konfirmasi Selesai", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mulai(new KonfrimasiSelesai(getTransaksi.getId(), getTransaksi.getId_user(), "1"));

                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void mulai(KonfrimasiSelesai konfrimasiSelesai) {

        pDialog = new ProgressDialog(context);
        //  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setMessage("Loading");
        pDialog.setCancelable(false);
        // pDialog.setIndeterminate(false);
        pDialog.show();
        Call<ResponseSelesai> mulai = client.getApi().konfrimasiselesai(konfrimasiSelesai);
        mulai.enqueue(new Callback<ResponseSelesai>() {
            @Override
            public void onResponse(Call<ResponseSelesai> call, Response<ResponseSelesai> response) {
                pDialog.hide();
                if (response.isSuccessful()) {
                    Toasty.success(context, "Behasil", Toast.LENGTH_LONG).show();

                } else {
                    Toasty.error(context, "Gagal Update Data", Toast.LENGTH_LONG).show();
                    System.out.println("data user " + response.code());
                }
            }

            @Override
            public void onFailure(Call<ResponseSelesai> call, Throwable t) {
                pDialog.hide();
                Toasty.error(context, "Koneksi Tidak ada", Toast.LENGTH_LONG).show();


            }
        });

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
