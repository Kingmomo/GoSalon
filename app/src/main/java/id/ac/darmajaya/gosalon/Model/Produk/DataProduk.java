package id.ac.darmajaya.gosalon.Model.Produk;

import com.google.gson.annotations.SerializedName;

public class DataProduk {

    @SerializedName("id")
    private String id;

    @SerializedName("id_toko")
    private String id_toko;

    @SerializedName("nama_produk")
    private String nama_produk;

    @SerializedName("harga_produk")
    private String harga_produk;

    @SerializedName("waktu_pengerjaan")
    private String waktu_pengerjaan;

    @SerializedName("foto_produk")
    private String foto_produk;

    @SerializedName("deskripsi")
    private String deskripsi;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_toko() {
        return id_toko;
    }

    public void setId_toko(String id_toko) {
        this.id_toko = id_toko;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getHarga_produk() {
        return harga_produk;
    }

    public void setHarga_produk(String harga_produk) {
        this.harga_produk = harga_produk;
    }

    public String getWaktu_pengerjaan() {
        return waktu_pengerjaan;
    }

    public void setWaktu_pengerjaan(String waktu_pengerjaan) {
        this.waktu_pengerjaan = waktu_pengerjaan;
    }

    public String getFoto_produk() {
        return foto_produk;
    }

    public void setFoto_produk(String foto_produk) {
        this.foto_produk = foto_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
